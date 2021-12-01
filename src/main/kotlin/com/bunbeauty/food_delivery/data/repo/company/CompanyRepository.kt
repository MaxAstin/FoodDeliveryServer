package com.bunbeauty.food_delivery.data.repo.company

import com.bunbeauty.food_delivery.data.DatabaseFactory.query
import com.bunbeauty.food_delivery.data.entity.CompanyEntity
import com.bunbeauty.food_delivery.data.model.company.GetCompany
import com.bunbeauty.food_delivery.data.model.company.InsertCompany
import java.util.*

class CompanyRepository : ICompanyRepository {

    override suspend fun insertCompany(insertCompany: InsertCompany): GetCompany = query {
        CompanyEntity.new {
            name = insertCompany.name
            forFreeDelivery = insertCompany.forFreeDelivery
            deliveryCost = insertCompany.deliveryCost
        }.toCompany()
    }

    override suspend fun getCompanyByUuid(uuid: UUID): GetCompany? = query {
        CompanyEntity.findById(uuid)?.toCompany()
    }
}