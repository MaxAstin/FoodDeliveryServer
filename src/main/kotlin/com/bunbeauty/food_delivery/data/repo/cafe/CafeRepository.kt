package com.bunbeauty.food_delivery.data.repo.cafe

import com.bunbeauty.food_delivery.data.DatabaseFactory.query
import com.bunbeauty.food_delivery.data.entity.CafeEntity
import com.bunbeauty.food_delivery.data.model.cafe.GetCafe
import com.bunbeauty.food_delivery.data.table.CafeTable
import java.util.*

class CafeRepository : ICafeRepository {

    override suspend fun getCafeListByCityUuid(cityUuid: UUID): List<GetCafe> = query {
        CafeEntity.find {
            CafeTable.city eq cityUuid
        }.map { cafeEntity ->
            cafeEntity.toCafe()
        }.toList()
    }
}