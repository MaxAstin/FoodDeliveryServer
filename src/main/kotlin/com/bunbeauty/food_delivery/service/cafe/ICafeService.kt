package com.bunbeauty.food_delivery.service.cafe

import com.bunbeauty.food_delivery.data.model.cafe.GetCafe
import com.bunbeauty.food_delivery.data.model.cafe.PostCafe

interface ICafeService {

    suspend fun createCafe(postCafe: PostCafe): GetCafe
    suspend fun getCafeListByCityUuid(cityUuid: String): List<GetCafe>
}