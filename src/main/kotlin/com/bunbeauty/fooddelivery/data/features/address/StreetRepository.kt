package com.bunbeauty.fooddelivery.data.features.address

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.CityEntity
import com.bunbeauty.fooddelivery.data.entity.StreetEntity
import com.bunbeauty.fooddelivery.data.entity.cafe.CafeEntity
import com.bunbeauty.fooddelivery.data.features.address.mapper.mapStreetEntity
import com.bunbeauty.fooddelivery.domain.feature.address.model.Street
import com.bunbeauty.fooddelivery.domain.model.street.InsertStreet
import com.bunbeauty.fooddelivery.domain.toUuid
import java.util.*

class StreetRepository {

    suspend fun insertStreet(insertStreet: InsertStreet): Street = query {
        StreetEntity.new {
            name = insertStreet.name
            cafe = CafeEntity[insertStreet.cafeUuid]
            isVisible = insertStreet.isVisible
        }.mapStreetEntity()
    }

    suspend fun getStreetListByCityUuid(cityUuid: UUID): List<Street> = query {
        CityEntity.findById(cityUuid)?.cafes?.flatMap { cafeEntity ->
            cafeEntity.streets
        }?.map(mapStreetEntity) ?: emptyList()
    }

    suspend fun getStreetByUuid(streetUuid: UUID): Street? = query {
        StreetEntity.findById(streetUuid)?.mapStreetEntity()
    }

    suspend fun getAllStreets(): List<Street> = query {
        StreetEntity.all().map(mapStreetEntity)
    }

    suspend fun updateStreetCoordinates(uuid: String, latitude: Double, longitude: Double) = query {
        StreetEntity.findById(uuid.toUuid())?.apply {
            this.latitude = latitude
            this.longitude = longitude
        }
    }

}