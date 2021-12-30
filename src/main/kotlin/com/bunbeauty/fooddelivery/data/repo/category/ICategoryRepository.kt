package com.bunbeauty.fooddelivery.data.repo.category

import com.bunbeauty.fooddelivery.data.model.category.GetCategory
import com.bunbeauty.fooddelivery.data.model.category.InsertCategory
import com.bunbeauty.fooddelivery.data.model.category.UpdateCategory
import java.util.*

interface ICategoryRepository {

    suspend fun insertCategory(category: InsertCategory) : GetCategory
    suspend fun updateCategory(categoryUuid: UUID, category: UpdateCategory) : GetCategory?
    suspend fun getCategoryListByCompanyUuid(companyUuid: UUID) : List<GetCategory>
}