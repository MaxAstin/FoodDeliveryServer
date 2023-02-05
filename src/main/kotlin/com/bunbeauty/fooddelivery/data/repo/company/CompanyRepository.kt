package com.bunbeauty.fooddelivery.data.repo.company

import com.bunbeauty.fooddelivery.data.Constants
import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.CompanyEntity
import com.bunbeauty.fooddelivery.data.model.company.GetCompany
import com.bunbeauty.fooddelivery.data.model.company.InsertCompany
import com.bunbeauty.fooddelivery.data.model.company.UpdateCompany
import com.bunbeauty.fooddelivery.data.table.CompanyTable
import java.util.*

class CompanyRepository : ICompanyRepository {

    override suspend fun insertCompany(insertCompany: InsertCompany): GetCompany = query {
        CompanyEntity.new {
            name = insertCompany.name
            forFreeDelivery = insertCompany.forFreeDelivery
            deliveryCost = insertCompany.deliveryCost
            forceUpdateVersion = insertCompany.forceUpdateVersion
        }.toCompany()
    }

    override suspend fun updateCompany(updateCompany: UpdateCompany): GetCompany? = query {
        CompanyEntity.findById(updateCompany.uuid)?.apply {
            name = updateCompany.name ?: name
            forFreeDelivery = updateCompany.forFreeDelivery ?: forFreeDelivery
            deliveryCost = updateCompany.deliveryCost ?: deliveryCost
            forceUpdateVersion = updateCompany.forceUpdateVersion ?: forceUpdateVersion
        }?.toCompany()
    }

    override suspend fun getCompanyByUuid(uuid: UUID): GetCompany? = query {
        CompanyEntity.findById(uuid)?.toCompany()
    }

    override suspend fun getCompanyByName(name: String): GetCompany? = query {
        CompanyEntity.find {
            CompanyTable.name eq Constants.MAIN_COMPANY_NAME
        }.singleOrNull()?.toCompany()
    }

    override suspend fun getCompanyList(): List<GetCompany> = query {
        CompanyEntity.all().map { companyEntity ->
            companyEntity.toCompany()
        }
    }
}