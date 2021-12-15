package com.bunbeauty.fooddelivery.data.repo.category

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.CategoryEntity
import com.bunbeauty.fooddelivery.data.entity.CompanyEntity
import com.bunbeauty.fooddelivery.data.model.category.GetCategory
import com.bunbeauty.fooddelivery.data.model.category.InsertCategory
import com.bunbeauty.fooddelivery.data.table.CategoryTable
import java.util.*

class CategoryRepository : ICategoryRepository {

    override suspend fun insertCategory(category: InsertCategory): GetCategory = query {
        CategoryEntity.new {
            name = category.name
            company = CompanyEntity[category.companyUuid]
        }.toCategory()
    }

    override suspend fun getCategoryListByCompanyUuid(companyUuid: UUID): List<GetCategory> = query {
        CategoryEntity.find {
            CategoryTable.company eq companyUuid
        }.map { categoryEntity ->
            categoryEntity.toCategory()
        }.toList()
    }
}