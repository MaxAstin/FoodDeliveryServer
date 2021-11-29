package com.bunbeauty.food_delivery.data.repo.cafe

import com.bunbeauty.food_delivery.data.DatabaseFactory.query
import com.bunbeauty.food_delivery.data.entity.CafeEntity
import com.bunbeauty.food_delivery.data.entity.CityEntity
import com.bunbeauty.food_delivery.data.model.cafe.GetCafe
import com.bunbeauty.food_delivery.data.model.cafe.InsertCafe
import com.bunbeauty.food_delivery.data.table.CafeTable
import java.util.*

class CafeRepository : ICafeRepository {

    override suspend fun insertCafe(insertCafe: InsertCafe): GetCafe = query {
        CafeEntity.new {
            fromTime = insertCafe.fromTime
            toTime = insertCafe.toTime
            phone = insertCafe.phone
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
}