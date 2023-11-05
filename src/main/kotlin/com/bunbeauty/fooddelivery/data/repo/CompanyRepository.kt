package com.bunbeauty.fooddelivery.data.repo

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.company.CompanyEntity
import com.bunbeauty.fooddelivery.domain.model.company.GetCompany
import com.bunbeauty.fooddelivery.domain.model.company.InsertCompany
import com.bunbeauty.fooddelivery.domain.model.company.UpdateCompany
import java.util.*

class CompanyRepository {

    suspend fun insertCompany(insertCompany: InsertCompany): GetCompany = query {
        CompanyEntity.new {
            name = insertCompany.name
            forFreeDelivery = insertCompany.forFreeDelivery
            deliveryCost = insertCompany.deliveryCost
            forceUpdateVersion = insertCompany.forceUpdateVersion
            percentDiscount = insertCompany.percentDiscount?.takeIf { percentDiscount ->
                percentDiscount != 0
            }
        }.toCompany()
    }

    suspend fun updateCompany(updateCompany: UpdateCompany): GetCompany? = query {
        CompanyEntity.findById(updateCompany.uuid)?.apply {
            name = updateCompany.name ?: name
            forFreeDelivery = updateCompany.forFreeDelivery ?: forFreeDelivery
            deliveryCost = updateCompany.deliveryCost ?: deliveryCost
            forceUpdateVersion = updateCompany.forceUpdateVersion ?: forceUpdateVersion
            percentDiscount = (updateCompany.percentDiscount ?: percentDiscount).takeIf { percentDiscount ->
                percentDiscount != 0
            }
        }?.toCompany()
    }

    suspend fun getCompanyByUuid(uuid: UUID): GetCompany? = query {
        CompanyEntity.findById(uuid)?.toCompany()
    }

    suspend fun getCompanyList(): List<GetCompany> = query {
        CompanyEntity.all().map { companyEntity ->
            companyEntity.toCompany()
        }
    }
}