package com.bunbeauty.fooddelivery.domain.feature.menu.service

import com.bunbeauty.fooddelivery.data.features.menu.CategoryRepository
import com.bunbeauty.fooddelivery.data.features.user.UserRepository
import com.bunbeauty.fooddelivery.domain.error.orThrowNotFoundByUserUuidError
import com.bunbeauty.fooddelivery.domain.feature.menu.mapper.mapCategory
import com.bunbeauty.fooddelivery.domain.feature.menu.model.category.GetCategory
import com.bunbeauty.fooddelivery.domain.feature.menu.model.category.InsertCategory
import com.bunbeauty.fooddelivery.domain.feature.menu.model.category.PatchCategory
import com.bunbeauty.fooddelivery.domain.feature.menu.model.category.PatchCategoryList
import com.bunbeauty.fooddelivery.domain.feature.menu.model.category.PostCategory
import com.bunbeauty.fooddelivery.domain.feature.menu.model.category.UpdateCategory
import com.bunbeauty.fooddelivery.domain.feature.menu.usecase.UpdateCategoryUseCase
import com.bunbeauty.fooddelivery.domain.toUuid

class CategoryService(
    private val categoryRepository: CategoryRepository,
    private val userRepository: UserRepository,
    private val updateCategoryUseCase: UpdateCategoryUseCase
) {

    suspend fun getCategoryListByCompanyUuid(companyUuid: String): List<GetCategory> {
        val categoryList = categoryRepository.getCategoryListByCompanyUuid(
            companyUuid = companyUuid.toUuid()
        ).map(mapCategory)
        val hitsCategory = categoryRepository.getHitsCategory().mapCategory()

        return categoryList + hitsCategory
    }

    suspend fun createCategory(postCategory: PostCategory, creatorUuid: String): GetCategory {
        val companyUuid = userRepository.getCompanyByUserUuid(creatorUuid.toUuid())
            .orThrowNotFoundByUserUuidError(creatorUuid)
            .uuid
            .toUuid()
        val insertCategory = InsertCategory(
            name = postCategory.name,
            priority = postCategory.priority,
            companyUuid = companyUuid
        )

        return categoryRepository.insertCategory(insertCategory).mapCategory()
    }

    suspend fun updateCategory(categoryUuid: String, patchCategory: PatchCategory): GetCategory {
        return updateCategoryUseCase(
            uuid = categoryUuid,
            updateCategory = UpdateCategory(
                name = patchCategory.name,
                priority = patchCategory.priority
            )
        ).mapCategory()
    }

    suspend fun updateCategoryList(patchCategoryList: PatchCategoryList): List<GetCategory> {
        return patchCategoryList.patchCategoryItemList.map { patchCategoryItem ->
            updateCategoryUseCase(
                uuid = patchCategoryItem.uuid,
                updateCategory = UpdateCategory(
                    name = patchCategoryItem.patchCategory.name,
                    priority = patchCategoryItem.patchCategory.priority
                )
            ).mapCategory()
        }
    }
}
