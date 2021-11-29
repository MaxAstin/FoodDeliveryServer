package com.bunbeauty.food_delivery.service.cafe

import com.bunbeauty.food_delivery.data.ext.toUuid
import com.bunbeauty.food_delivery.data.model.cafe.GetCafe
import com.bunbeauty.food_delivery.data.model.cafe.InsertCafe
import com.bunbeauty.food_delivery.data.model.cafe.PostCafe
import com.bunbeauty.food_delivery.data.repo.cafe.ICafeRepository
import com.bunbeauty.food_delivery.data.repo.user.IUserRepository

class CafeService(private val cafeRepository: ICafeRepository) : ICafeService {

    override suspend fun createCafe(postCafe: PostCafe): GetCafe {
        val insertCafe = InsertCafe(
            fromTime = postCafe.fromTime,
            toTime = postCafe.toTime,
            phone = postCafe.phone,
            latitude = postCafe.latitude,
            longitude = postCafe.longitude,
            address = postCafe.address,
            cityUuid = postCafe.cityUuid.toUuid(),
            isVisible = postCafe.isVisible,
        )

        return cafeRepository.insertCafe(insertCafe)
    }

    override suspend fun getCafeListByCityUuid(cityUuid: String): List<GetCafe> =
        cafeRepository.getCafeListByCityUuid(cityUuid.toUuid())
}