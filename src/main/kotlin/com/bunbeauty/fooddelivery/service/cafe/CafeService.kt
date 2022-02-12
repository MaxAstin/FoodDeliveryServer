package com.bunbeauty.fooddelivery.service.cafe

import com.bunbeauty.fooddelivery.data.ext.toUuid
import com.bunbeauty.fooddelivery.data.model.cafe.GetCafe
import com.bunbeauty.fooddelivery.data.model.cafe.InsertCafe
import com.bunbeauty.fooddelivery.data.model.cafe.PostCafe
import com.bunbeauty.fooddelivery.data.repo.cafe.ICafeRepository

class CafeService(private val cafeRepository: ICafeRepository) : ICafeService {

    override suspend fun createCafe(postCafe: PostCafe): GetCafe {
        val insertCafe = InsertCafe(
            fromTime = postCafe.fromTime,
            toTime = postCafe.toTime,
            phoneNumber = postCafe.phoneNumber,
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