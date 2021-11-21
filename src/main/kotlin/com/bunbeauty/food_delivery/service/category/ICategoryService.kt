package com.bunbeauty.food_delivery.service.category

import com.bunbeauty.food_delivery.data.model.category.GetCategory

interface ICategoryService {

    suspend fun getCategoryList(): List<GetCategory>
}