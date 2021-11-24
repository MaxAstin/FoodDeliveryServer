package com.bunbeauty.food_delivery.data.repo.company

import com.bunbeauty.food_delivery.data.DatabaseFactory.query
import com.bunbeauty.food_delivery.data.entity.conpany.CompanyEntity
import com.bunbeauty.food_delivery.data.entity.conpany.InsertCompany

class CompanyRepository : ICompanyRepository {

    override suspend fun insertCompany(insertCompany: InsertCompany): CompanyEntity = query {
        CompanyEntity.new {
            name = insertCompany.name
        }
    }
}