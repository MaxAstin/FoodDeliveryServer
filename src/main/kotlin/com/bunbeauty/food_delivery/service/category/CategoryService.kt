package com.bunbeauty.food_delivery.service.category

import com.bunbeauty.food_delivery.data.model.category.GetCategory
import com.bunbeauty.food_delivery.data.repo.category.CategoryRepository
import com.bunbeauty.food_delivery.data.repo.category.ICategoryRepository

class CategoryService(private val categoryRepository: ICategoryRepository) : ICategoryService {

    override suspend fun getCategoryList(): List<GetCategory> {
        return categoryRepository.getCategoryList()
    }
}