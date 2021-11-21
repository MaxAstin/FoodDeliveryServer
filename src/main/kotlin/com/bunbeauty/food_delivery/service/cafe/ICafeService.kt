package com.bunbeauty.food_delivery.service.cafe

import com.bunbeauty.food_delivery.data.model.cafe.GetCafe

interface ICafeService {

    suspend fun getCafeListByCityUuid(cityUuid: String): List<GetCafe>
}