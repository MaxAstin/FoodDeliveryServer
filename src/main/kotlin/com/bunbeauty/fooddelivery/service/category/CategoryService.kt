package com.bunbeauty.fooddelivery.service.category

import com.bunbeauty.fooddelivery.data.ext.toUuid
import com.bunbeauty.fooddelivery.data.model.category.GetCategory
import com.bunbeauty.fooddelivery.data.model.category.InsertCategory
import com.bunbeauty.fooddelivery.data.model.category.PostCategory
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
            companyUuid = companyUuid.toUuid()
        )

        return categoryRepository.insertCategory(insertCategory)
    }

    override suspend fun getCategoryListByCompanyUuid(companyUuid: String): List<GetCategory> {
        return categoryRepository.getCategoryListByCompanyUuid(companyUuid.toUuid())
    }
}