package com.bunbeauty.food_delivery.data.repo.company

import com.bunbeauty.food_delivery.data.DatabaseFactory.query
import com.bunbeauty.food_delivery.data.entity.CompanyEntity
import com.bunbeauty.food_delivery.data.model.company.GetCompany
import com.bunbeauty.food_delivery.data.model.company.InsertCompany

class CompanyRepository : ICompanyRepository {

    override suspend fun insertCompany(insertCompany: InsertCompany): GetCompany = query {
        CompanyEntity.new {
            name = insertCompany.name
        }.toCompany()
    }
}