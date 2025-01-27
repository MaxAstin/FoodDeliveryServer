package com.bunbeauty.fooddelivery.data.repo

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.company.CompanyEntity
import com.bunbeauty.fooddelivery.data.features.company.mapper.mapCompanyEntity
import com.bunbeauty.fooddelivery.domain.feature.company.Company
import com.bunbeauty.fooddelivery.domain.model.company.InsertCompany
import com.bunbeauty.fooddelivery.domain.model.company.UpdateCompany
import java.util.*

class CompanyRepository {

    suspend fun insertCompany(insertCompany: InsertCompany): Company = query {
        CompanyEntity.new {
            name = insertCompany.name
            forFreeDelivery = insertCompany.forFreeDelivery
            deliveryCost = insertCompany.deliveryCost
            forceUpdateVersion = insertCompany.forceUpdateVersion
            percentDiscount = insertCompany.percentDiscount?.takeIf { percentDiscount ->
                percentDiscount != 0
            }
        }.mapCompanyEntity()
    }

    suspend fun updateCompany(updateCompany: UpdateCompany): Company? = query {
        CompanyEntity.findById(updateCompany.uuid)?.apply {
            name = updateCompany.name ?: name
            forFreeDelivery = updateCompany.forFreeDelivery ?: forFreeDelivery
            deliveryCost = updateCompany.deliveryCost ?: deliveryCost
            forceUpdateVersion = updateCompany.forceUpdateVersion ?: forceUpdateVersion
            percentDiscount = (updateCompany.percentDiscount ?: percentDiscount).takeIf { percentDiscount ->
                percentDiscount != 0
            }
            isOpen = updateCompany.isOpen ?: isOpen
            workType = updateCompany.workType?.name ?: workType
        }?.mapCompanyEntity()
    }

    suspend fun getCompanyByUuid(uuid: UUID): Company? = query {
        CompanyEntity.findById(uuid)?.mapCompanyEntity()
    }

    suspend fun getCompanyList(): List<Company> = query {
        CompanyEntity.all().map(mapCompanyEntity)
    }
}
