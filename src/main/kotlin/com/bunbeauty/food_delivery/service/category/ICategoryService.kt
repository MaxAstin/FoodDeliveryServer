package com.bunbeauty.food_delivery.service.category

import com.bunbeauty.food_delivery.data.model.category.GetCategory
import com.bunbeauty.food_delivery.data.model.category.PostCategory

interface ICategoryService {

    suspend fun createCategory(postCategory: PostCategory, creatorUuid: String): GetCategory?
    suspend fun getCategoryListByCompanyUuid(companyUuid: String): List<GetCategory>
}