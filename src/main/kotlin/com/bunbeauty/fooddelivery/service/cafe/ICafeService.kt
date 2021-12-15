package com.bunbeauty.fooddelivery.service.cafe

import com.bunbeauty.fooddelivery.data.model.cafe.GetCafe
import com.bunbeauty.fooddelivery.data.model.cafe.PostCafe

interface ICafeService {

    suspend fun createCafe(postCafe: PostCafe): GetCafe
    suspend fun getCafeListByCityUuid(cityUuid: String): List<GetCafe>
}