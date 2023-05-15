package com.bunbeauty.fooddelivery.data.repo.cafe

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.CafeEntity
import com.bunbeauty.fooddelivery.data.entity.CityEntity
import com.bunbeauty.fooddelivery.data.model.cafe.GetCafe
import com.bunbeauty.fooddelivery.data.model.cafe.InsertCafe
import com.bunbeauty.fooddelivery.data.table.CafeTable
import com.bunbeauty.fooddelivery.data.table.CityTable
import java.util.UUID

class CafeRepository : ICafeRepository {

    override suspend fun insertCafe(insertCafe: InsertCafe): GetCafe = query {
        CafeEntity.new {
            fromTime = insertCafe.fromTime
            toTime = insertCafe.toTime
            phoneNumber = insertCafe.phoneNumber
            latitude = insertCafe.latitude
            longitude = insertCafe.longitude
            address = insertCafe.address
            city = CityEntity[insertCafe.cityUuid]
            isVisible = insertCafe.isVisible
        }.toCafe()
    }

    override suspend fun getCafeListByCityUuid(cityUuid: UUID): List<GetCafe> = query {
        CafeEntity.find {
            CafeTable.city eq cityUuid
        }.map { cafeEntity ->
            cafeEntity.toCafe()
        }.toList()
    }

    override suspend fun getCafeListByCompanyUuid(companyUuid: UUID): List<GetCafe> = query {
        CityEntity.find {
            CityTable.company eq companyUuid
        }.flatMap { cityEntity ->
            cityEntity.cafes.map { cafeEntity ->
                cafeEntity.toCafe()
            }
        }.toList()
    }

    override suspend fun incrementCafeCodeCounter(cafeUuid: UUID, divisor: Int): Int? = query {
        CafeEntity.findById(cafeUuid)?.apply {
            codeCounter = (codeCounter + 1) % divisor
        }?.codeCounter
    }
}