package com.bunbeauty.food_delivery.service.category

import com.bunbeauty.food_delivery.data.ext.toUuid
import com.bunbeauty.food_delivery.data.model.category.GetCategory
import com.bunbeauty.food_delivery.data.model.category.InsertCategory
import com.bunbeauty.food_delivery.data.model.category.PostCategory
import com.bunbeauty.food_delivery.data.repo.category.ICategoryRepository
import com.bunbeauty.food_delivery.data.repo.user.IUserRepository

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