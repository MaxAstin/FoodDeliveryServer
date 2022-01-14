package com.bunbeauty.fooddelivery.service.category

import com.bunbeauty.fooddelivery.data.ext.toUuid
import com.bunbeauty.fooddelivery.data.model.category.*
import com.bunbeauty.fooddelivery.data.repo.category.ICategoryRepository
import com.bunbeauty.fooddelivery.data.repo.user.IUserRepository

class CategoryService(
    private val categoryRepository: ICategoryRepository,
    private val userRepository: IUserRepository,
) : ICategoryService {

    override suspend fun createCategory(postCategory: PostCategory, creatorUuid: String): GetCategory? {
        val companyUuid = userRepository.getCompanyUuidByUserUuid(creatorUuid.toUuid()) ?: return null
        val insertCategory = InsertCategory(
            name = postCategory.name,
            priority = postCategory.priority,
            companyUuid = companyUuid.toUuid()
        )

        return categoryRepository.insertCategory(insertCategory)
    }

    override suspend fun updateCategory(categoryUuid: String, patchCategory: PatchCategory): GetCategory? {
        val updateCategory = UpdateCategory(
            name = patchCategory.name,
            priority = patchCategory.priority
        )

        return categoryRepository.updateCategory(categoryUuid.toUuid(), updateCategory)
    }

    override suspend fun getCategoryListByCompanyUuid(companyUuid: String): List<GetCategory> {
        val categoryList = categoryRepository.getCategoryListByCompanyUuid(companyUuid.toUuid())
        val hitsCategory = categoryRepository.getHitsCategory()

        return categoryList + hitsCategory
    }
}