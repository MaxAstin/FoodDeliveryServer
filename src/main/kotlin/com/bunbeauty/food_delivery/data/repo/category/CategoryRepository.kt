package com.bunbeauty.food_delivery.data.repo.category

import com.bunbeauty.food_delivery.data.DatabaseFactory.query
import com.bunbeauty.food_delivery.data.entity.CategoryEntity
import com.bunbeauty.food_delivery.data.entity.CompanyEntity
import com.bunbeauty.food_delivery.data.model.category.GetCategory
import com.bunbeauty.food_delivery.data.model.category.InsertCategory
import com.bunbeauty.food_delivery.data.table.CategoryTable
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