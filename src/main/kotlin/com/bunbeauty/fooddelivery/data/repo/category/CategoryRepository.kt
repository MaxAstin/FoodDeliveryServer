package com.bunbeauty.fooddelivery.data.repo.category

import com.bunbeauty.fooddelivery.data.Constants
import com.bunbeauty.fooddelivery.data.Constants.HITS_CATEGORY_NAME
import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.CategoryEntity
import com.bunbeauty.fooddelivery.data.entity.CompanyEntity
import com.bunbeauty.fooddelivery.data.model.category.GetCategory
import com.bunbeauty.fooddelivery.data.model.category.InsertCategory
import com.bunbeauty.fooddelivery.data.model.category.UpdateCategory
import com.bunbeauty.fooddelivery.data.table.CategoryTable
import java.util.*

class CategoryRepository : ICategoryRepository {

    override suspend fun insertCategory(category: InsertCategory): GetCategory = query {
        CategoryEntity.new {
            name = category.name
            priority = category.priority
            company = CompanyEntity[category.companyUuid]
        }.toCategory()
    }

    override suspend fun updateCategory(categoryUuid: UUID, category: UpdateCategory): GetCategory? = query {
        CategoryEntity.findById(categoryUuid)?.apply {
            name = category.name ?: name
            priority = category.priority ?: priority
        }?.toCategory()
    }

    override suspend fun getCategoryListByCompanyUuid(companyUuid: UUID): List<GetCategory> = query {
        CategoryEntity.find {
            CategoryTable.company eq companyUuid
        }.map { categoryEntity ->
            categoryEntity.toCategory()
        }.toList()
    }

    override fun getHitsCategory(): GetCategory {
        return GetCategory(
            uuid = "",
            name = HITS_CATEGORY_NAME,
            priority = 1
        )
    }
}