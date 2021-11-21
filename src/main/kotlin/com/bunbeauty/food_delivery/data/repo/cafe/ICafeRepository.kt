package com.bunbeauty.food_delivery.data.repo.cafe

import com.bunbeauty.food_delivery.data.model.cafe.GetCafe

interface ICafeRepository {

    suspend fun getCafeListByCityUuid(cityUuid: String): List<GetCafe>
}