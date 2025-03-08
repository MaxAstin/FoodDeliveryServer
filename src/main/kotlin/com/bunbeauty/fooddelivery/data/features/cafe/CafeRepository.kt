package com.bunbeauty.fooddelivery.data.features.cafe

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.CityEntity
import com.bunbeauty.fooddelivery.data.entity.cafe.CafeEntity
import com.bunbeauty.fooddelivery.data.features.cafe.mapper.mapCafeEntityToCafe
import com.bunbeauty.fooddelivery.data.features.cafe.mapper.mapCafeWithZonesEntity
import com.bunbeauty.fooddelivery.data.table.CityTable
import com.bunbeauty.fooddelivery.data.table.cafe.CafeTable
import com.bunbeauty.fooddelivery.domain.feature.cafe.model.cafe.Cafe
import com.bunbeauty.fooddelivery.domain.feature.cafe.model.cafe.CafeWithZones
import com.bunbeauty.fooddelivery.domain.feature.cafe.model.cafe.InsertCafe
import com.bunbeauty.fooddelivery.domain.feature.cafe.model.cafe.UpdateCafe
import com.bunbeauty.fooddelivery.domain.toUuid
import java.util.*
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select

class CafeRepository {

    suspend fun insertCafe(insertCafe: InsertCafe): CafeWithZones = query {
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
        }.mapCafeWithZonesEntity()
    }

    suspend fun getCafeByUuid(uuid: UUID): Cafe? {
        return query {
            (CafeTable).slice(
                CafeTable.id,
                CafeTable.fromTime,
                CafeTable.toTime,
                CafeTable.offset,
                CafeTable.phoneNumber,
                CafeTable.latitude,
                CafeTable.longitude,
                CafeTable.address,
                CafeTable.codeCounter,
                CafeTable.isVisible,
                CafeTable.workType,
                CafeTable.workload,
            ).select {
                CafeTable.id eq uuid
            }.singleOrNull()?.mapCafeEntityToCafe()
        }
    }

    suspend fun getCafeListByCityUuid(cityUuid: String): List<CafeWithZones> = query {
        CafeEntity.find {
            CafeTable.city eq cityUuid.toUuid()
        }.map(mapCafeWithZonesEntity)
            .toList()
    }

    suspend fun getCafeListByCompanyUuid(companyUuid: String): List<CafeWithZones> = query {
        CityEntity.find {
            CityTable.company eq companyUuid.toUuid()
        }.flatMap { cityEntity ->
            cityEntity.cafes.map(mapCafeWithZonesEntity)
        }.toList()
    }

    suspend fun incrementCafeCodeCounter(cafeUuid: String, limit: Int): Int? = query {
        CafeEntity.findById(cafeUuid.toUuid())?.apply {
            codeCounter = (codeCounter + 1) % limit
        }?.codeCounter
    }

    suspend fun updateCafe(cafeUuid: String, updateCafe: UpdateCafe): CafeWithZones? = query {
        CafeEntity.findById(cafeUuid.toUuid())?.apply {
            fromTime = updateCafe.fromTime ?: fromTime
            toTime = updateCafe.toTime ?: toTime
            offset = updateCafe.offset ?: offset
            phoneNumber = updateCafe.phone ?: phoneNumber
            latitude = updateCafe.latitude ?: latitude
            longitude = updateCafe.longitude ?: longitude
            address = updateCafe.address ?: address
            isVisible = updateCafe.isVisible ?: isVisible
        }?.mapCafeWithZonesEntity()
    }
}
