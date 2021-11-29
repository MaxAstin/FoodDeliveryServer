package com.bunbeauty.food_delivery.data.repo.category

import com.bunbeauty.food_delivery.data.model.category.GetCategory
import com.bunbeauty.food_delivery.data.model.category.InsertCategory
import java.util.*

interface ICategoryRepository {

    suspend fun insertCategory(category: InsertCategory) : GetCategory
    suspend fun getCategoryListByCompanyUuid(companyUuid: UUID) : List<GetCategory>
}