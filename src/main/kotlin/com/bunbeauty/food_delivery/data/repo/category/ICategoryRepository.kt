package com.bunbeauty.food_delivery.data.repo.category

import com.bunbeauty.food_delivery.data.model.category.GetCategory

interface ICategoryRepository {

    suspend fun getCategoryList() : List<GetCategory>
}