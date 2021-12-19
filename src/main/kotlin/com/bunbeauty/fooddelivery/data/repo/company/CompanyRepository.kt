package com.bunbeauty.fooddelivery.data.repo.company

import com.bunbeauty.fooddelivery.data.Constants
import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.CompanyEntity
import com.bunbeauty.fooddelivery.data.model.company.GetCompany
import com.bunbeauty.fooddelivery.data.model.company.InsertCompany
import com.bunbeauty.fooddelivery.data.table.CompanyTable
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

    override suspend fun getCompanyByName(name: String): GetCompany? = query {
        CompanyEntity.find {
            CompanyTable.name eq Constants.MAIN_COMPANY_NAME
        }.singleOrNull()?.toCompany()
    }
}