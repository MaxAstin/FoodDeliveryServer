package com.bunbeauty.fooddelivery.data.repo

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.company.CompanyEntity
import com.bunbeauty.fooddelivery.data.features.company.mapper.mapCompanyResultRow
import com.bunbeauty.fooddelivery.data.features.company.mapper.mapCompanyWithCafesEntity
import com.bunbeauty.fooddelivery.data.table.ClientUserTable
import com.bunbeauty.fooddelivery.data.table.CompanyTable
import com.bunbeauty.fooddelivery.domain.feature.company.Company
import com.bunbeauty.fooddelivery.domain.feature.company.CompanyWithCafes
import com.bunbeauty.fooddelivery.domain.model.company.InsertCompany
import com.bunbeauty.fooddelivery.domain.model.company.UpdateCompany
import com.bunbeauty.fooddelivery.domain.toUuid
import org.jetbrains.exposed.sql.select
import java.util.*

class CompanyRepository {

    suspend fun insertCompany(insertCompany: InsertCompany): CompanyWithCafes = query {
        CompanyEntity.new {
            name = insertCompany.name
            forFreeDelivery = insertCompany.forFreeDelivery
            deliveryCost = insertCompany.deliveryCost
            forceUpdateVersion = insertCompany.forceUpdateVersion
            percentDiscount = insertCompany.percentDiscount?.takeIf { percentDiscount ->
                percentDiscount != 0
            }
        }.mapCompanyWithCafesEntity()
    }

    suspend fun updateCompany(updateCompany: UpdateCompany): CompanyWithCafes? = query {
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
        }?.mapCompanyWithCafesEntity()
    }

    suspend fun getCompanyByUuid(uuid: UUID): Company? = query {
        (CompanyTable).slice(CompanyTable.columns)
            .select {
                CompanyTable.id eq uuid
            }.singleOrNull()?.mapCompanyResultRow()
    }

    suspend fun getCompanyByUserUuid(userUuid: String): Company? = query {
        (ClientUserTable innerJoin CompanyTable)
            .slice(CompanyTable.columns)
            .select { ClientUserTable.id eq userUuid.toUuid() }
            .singleOrNull()
            ?.mapCompanyResultRow()
    }

    suspend fun getCompanyList(): List<CompanyWithCafes> = query {
        CompanyEntity.all().map(mapCompanyWithCafesEntity)
    }
}
