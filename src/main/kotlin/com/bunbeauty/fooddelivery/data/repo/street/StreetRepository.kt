package com.bunbeauty.fooddelivery.data.repo.street

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.AddressEntity
import com.bunbeauty.fooddelivery.data.entity.CafeEntity
import com.bunbeauty.fooddelivery.data.entity.CompanyEntity
import com.bunbeauty.fooddelivery.data.entity.StreetEntity
import com.bunbeauty.fooddelivery.data.model.cafe.GetCafe
import com.bunbeauty.fooddelivery.data.model.street.GetStreet
import com.bunbeauty.fooddelivery.data.model.street.InsertStreet
import java.util.*

class StreetRepository : IStreetRepository {

    override suspend fun insertStreet(insertStreet: InsertStreet): GetStreet = query {
        StreetEntity.new {
            name = insertStreet.name
            cafe = CafeEntity[insertStreet.cafeUuid]
            isVisible = insertStreet.isVisible
        }.toStreet()
    }

    override suspend fun getStreetListByCompanyUuid(companyUuid: UUID): List<GetStreet> = query {
        CompanyEntity.findById(companyUuid)?.cities?.flatMap { cityEntity ->
            cityEntity.cafes.flatMap { cafeEntity ->
                cafeEntity.streets
            }
        }?.filter { streetEntity ->
            streetEntity.isVisible
        }?.map { streetEntity ->
            streetEntity.toStreet()
        } ?: emptyList()
    }

    override suspend fun getStreetByAddressUuid(addressUuid: UUID): GetCafe? = query {
        AddressEntity.findById(addressUuid)?.street?.cafe?.toCafe()
    }
}