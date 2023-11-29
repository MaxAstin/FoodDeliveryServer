package com.bunbeauty.fooddelivery.domain.feature.menu.service

import com.bunbeauty.fooddelivery.data.features.menu.CategoryRepository
import com.bunbeauty.fooddelivery.data.repo.UserRepository
import com.bunbeauty.fooddelivery.domain.error.orThrowNotFoundByUserUuidError
import com.bunbeauty.fooddelivery.domain.feature.menu.mapper.mapCategory
import com.bunbeauty.fooddelivery.domain.feature.menu.model.category.*
import com.bunbeauty.fooddelivery.domain.toUuid

class CategoryService(
    private val categoryRepository: CategoryRepository,
    private val userRepository: UserRepository,
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

    suspend fun updateCategory(categoryUuid: String, patchCategory: PatchCategory): GetCategory? {
        val updateCategory = UpdateCategory(
            name = patchCategory.name,
            priority = patchCategory.priority
        )

        return categoryRepository.updateCategory(
            categoryUuid = categoryUuid.toUuid(),
            category = updateCategory
        )?.mapCategory()
    }
}