package com.bunbeauty.fooddelivery.data.repo.cafe

import com.bunbeauty.fooddelivery.data.model.cafe.GetCafe
import com.bunbeauty.fooddelivery.data.model.cafe.InsertCafe
import java.util.*

interface ICafeRepository {

    suspend fun insertCafe(insertCafe: InsertCafe): GetCafe
    suspend fun getCafeListByCityUuid(cityUuid: UUID): List<GetCafe>
}