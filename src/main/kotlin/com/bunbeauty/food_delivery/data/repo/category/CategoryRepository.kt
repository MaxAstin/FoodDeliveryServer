package com.bunbeauty.food_delivery.data.repo.category

import com.bunbeauty.food_delivery.data.DatabaseFactory.query
import com.bunbeauty.food_delivery.data.entity.CategoryEntity
import com.bunbeauty.food_delivery.data.model.category.GetCategory

class CategoryRepository: ICategoryRepository {

    override suspend fun getCategoryList(): List<GetCategory> = query {
        CategoryEntity.all().map { categoryEntity ->
            categoryEntity.toCategory()
        }.toList()
    }
}