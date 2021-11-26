package com.bunbeauty.food_delivery.data.repo.cafe

import com.bunbeauty.food_delivery.data.model.cafe.GetCafe
import java.util.*

interface ICafeRepository {

    suspend fun getCafeListByCityUuid(cityUuid: UUID): List<GetCafe>
}