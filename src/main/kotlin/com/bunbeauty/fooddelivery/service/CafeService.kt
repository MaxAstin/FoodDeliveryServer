package com.bunbeauty.fooddelivery.service

import com.bunbeauty.fooddelivery.data.ext.toUuid
import com.bunbeauty.fooddelivery.data.model.cafe.*
import com.bunbeauty.fooddelivery.data.repo.CafeRepository

class CafeService(private val cafeRepository: CafeRepository) {

    suspend fun createCafe(postCafe: PostCafe): GetCafe {
        val insertCafe = InsertCafe(
            fromTime = postCafe.fromTime,
            toTime = postCafe.toTime,
            offset = postCafe.offset,
            phone = postCafe.phone,
            latitude = postCafe.latitude,
            longitude = postCafe.longitude,
            address = postCafe.address,
            cityUuid = postCafe.cityUuid.toUuid(),
            isVisible = postCafe.isVisible,
        )

        return cafeRepository.insertCafe(insertCafe)
    }

    suspend fun getCafeListByCityUuid(cityUuid: String): List<GetCafe> {
        return cafeRepository.getCafeListByCityUuid(cityUuid.toUuid())
    }

    suspend fun updateCafe(cafeUuid: String, patchCafe: PatchCafe): GetCafe? {
        val updateCafe = UpdateCafe(
            fromTime = patchCafe.fromTime,
            toTime = patchCafe.toTime,
            offset = patchCafe.offset,
            phone = patchCafe.phone,
            latitude = patchCafe.latitude,
            longitude = patchCafe.longitude,
            address = patchCafe.address,
            isVisible = patchCafe.isVisible,
        )

        return cafeRepository.updateCafe(
            cafeUuid = cafeUuid.toUuid(),
            updateCafe = updateCafe
        )
    }

}