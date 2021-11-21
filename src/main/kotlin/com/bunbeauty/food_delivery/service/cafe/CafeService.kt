package com.bunbeauty.food_delivery.service.cafe

import com.bunbeauty.food_delivery.data.model.cafe.GetCafe
import com.bunbeauty.food_delivery.data.repo.cafe.ICafeRepository

class CafeService(private val cafeRepository: ICafeRepository) : ICafeService {

    override suspend fun getCafeListByCityUuid(cityUuid: String): List<GetCafe> =
        cafeRepository.getCafeListByCityUuid(cityUuid)
}