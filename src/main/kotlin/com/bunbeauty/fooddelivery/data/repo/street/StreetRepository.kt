package com.bunbeauty.fooddelivery.data.repo.street

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.*
import com.bunbeauty.fooddelivery.data.model.cafe.GetCafe
import com.bunbeauty.fooddelivery.data.model.street.GetStreet
import com.bunbeauty.fooddelivery.data.model.street.InsertStreet
import com.bunbeauty.fooddelivery.data.table.StreetTable
import java.util.UUID

class StreetRepository : IStreetRepository {

    override suspend fun insertStreet(insertStreet: InsertStreet): GetStreet = query {
        StreetEntity.new {
            name = insertStreet.name
            cafe = CafeEntity[insertStreet.cafeUuid]
            isVisible = insertStreet.isVisible
        }.toStreet()
    }

    override suspend fun getStreetListByCityUuid(cityUuid: UUID): List<GetStreet> = query {
        CityEntity.findById(cityUuid)?.cafes?.flatMap { cafeEntity ->
            cafeEntity.streets
        }?.map { streetEntity ->
            streetEntity.toStreet()
        } ?: emptyList()
    }

    override suspend fun getStreetByAddressUuid(addressUuid: UUID): GetStreet? = query {
        AddressEntity.findById(addressUuid)?.street?.toStreet()
    }

    override suspend fun getStreetByUuid(streetUuid: UUID): GetStreet? = query {
        StreetEntity.findById(streetUuid)?.toStreet()
    }

}