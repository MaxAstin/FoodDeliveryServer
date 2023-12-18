package com.bunbeauty.fooddelivery.data.features.cafe

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.CityEntity
import com.bunbeauty.fooddelivery.data.entity.cafe.CafeEntity
import com.bunbeauty.fooddelivery.data.features.cafe.mapper.mapCafeEntity
import com.bunbeauty.fooddelivery.data.table.CityTable
import com.bunbeauty.fooddelivery.data.table.cafe.CafeTable
import com.bunbeauty.fooddelivery.domain.feature.cafe.model.cafe.Cafe
import com.bunbeauty.fooddelivery.domain.feature.cafe.model.cafe.InsertCafe
import com.bunbeauty.fooddelivery.domain.feature.cafe.model.cafe.UpdateCafe
import java.util.*

class CafeRepository {

    suspend fun insertCafe(insertCafe: InsertCafe): Cafe = query {
        CafeEntity.new {
            fromTime = insertCafe.fromTime
            toTime = insertCafe.toTime
            offset = insertCafe.offset
            phoneNumber = insertCafe.phone
            latitude = insertCafe.latitude
            longitude = insertCafe.longitude
            address = insertCafe.address
            city = CityEntity[insertCafe.cityUuid]
            isVisible = insertCafe.isVisible
        }.mapCafeEntity()
    }

    suspend fun getCafeListByCityUuid(cityUuid: UUID): List<Cafe> = query {
        CafeEntity.find {
            CafeTable.city eq cityUuid
        }.map(mapCafeEntity)
            .toList()
    }

    suspend fun getCafeListByCompanyUuid(companyUuid: UUID): List<Cafe> = query {
        CityEntity.find {
            CityTable.company eq companyUuid
        }.flatMap { cityEntity ->
            cityEntity.cafes.map(mapCafeEntity)
        }.toList()
    }

    suspend fun incrementCafeCodeCounter(cafeUuid: UUID, divisor: Int): Int? = query {
        CafeEntity.findById(cafeUuid)?.apply {
            codeCounter = (codeCounter + 1) % divisor
        }?.codeCounter
    }

    suspend fun updateCafe(cafeUuid: UUID, updateCafe: UpdateCafe): Cafe? = query {
        CafeEntity.findById(cafeUuid)?.apply {
            fromTime = updateCafe.fromTime ?: fromTime
            toTime = updateCafe.toTime ?: toTime
            offset = updateCafe.offset ?: offset
            phoneNumber = updateCafe.phone ?: phoneNumber
            latitude = updateCafe.latitude ?: latitude
            longitude = updateCafe.longitude ?: longitude
            address = updateCafe.address ?: address
            isVisible = updateCafe.isVisible ?: isVisible
        }?.mapCafeEntity()
    }

}