package com.bunbeauty.food_delivery.data.repo.cafe

import com.bunbeauty.food_delivery.data.model.cafe.GetCafe
import com.bunbeauty.food_delivery.data.model.cafe.InsertCafe
import java.util.*

interface ICafeRepository {

    suspend fun insertCafe(insertCafe: InsertCafe): GetCafe
    suspend fun getCafeListByCityUuid(cityUuid: UUID): List<GetCafe>
}