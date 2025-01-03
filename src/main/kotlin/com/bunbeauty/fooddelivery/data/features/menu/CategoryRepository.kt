package com.bunbeauty.fooddelivery.data.features.menu

import com.bunbeauty.fooddelivery.data.Constants.HITS_CATEGORY_NAME
import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.company.CompanyEntity
import com.bunbeauty.fooddelivery.data.entity.menu.CategoryEntity
import com.bunbeauty.fooddelivery.data.features.menu.mapper.mapCategoryEntity
import com.bunbeauty.fooddelivery.data.table.menu.CategoryTable
import com.bunbeauty.fooddelivery.domain.feature.menu.model.category.Category
import com.bunbeauty.fooddelivery.domain.feature.menu.model.category.InsertCategory
import com.bunbeauty.fooddelivery.domain.feature.menu.model.category.UpdateCategory
import java.util.*

class CategoryRepository {

    suspend fun insertCategory(category: InsertCategory): Category = query {
        CategoryEntity.new {
            name = category.name
            priority = category.priority
            company = CompanyEntity[category.companyUuid]
        }.mapCategoryEntity()
    }

    suspend fun updateCategory(categoryUuid: UUID, category: UpdateCategory): Category? = query {
        CategoryEntity.findById(categoryUuid)?.apply {
            name = category.name ?: name
            priority = category.priority ?: priority
        }?.mapCategoryEntity()
    }

    suspend fun getCategoryListByCompanyUuid(companyUuid: UUID): List<Category> = query {
        CategoryEntity.find {
            CategoryTable.company eq companyUuid
        }.map(mapCategoryEntity)
            .toList()
    }

    fun getHitsCategory(): Category {
        return Category(
            uuid = "",
            name = HITS_CATEGORY_NAME,
            priority = 1
        )
    }
}
