package com.bunbeauty.fooddelivery.data.repo.category

import com.bunbeauty.fooddelivery.data.model.category.GetCategory
import com.bunbeauty.fooddelivery.data.model.category.InsertCategory
import java.util.*

interface ICategoryRepository {

    suspend fun insertCategory(category: InsertCategory) : GetCategory
    suspend fun getCategoryListByCompanyUuid(companyUuid: UUID) : List<GetCategory>
}