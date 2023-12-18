package com.bunbeauty.fooddelivery.domain.feature.cafe

import com.bunbeauty.fooddelivery.data.features.cafe.CafeRepository
import com.bunbeauty.fooddelivery.domain.feature.cafe.mapper.cafe.mapCafe
import com.bunbeauty.fooddelivery.domain.feature.cafe.mapper.cafe.mapPatchCafe
import com.bunbeauty.fooddelivery.domain.feature.cafe.mapper.cafe.mapPostCafe
import com.bunbeauty.fooddelivery.domain.feature.cafe.model.cafe.GetCafe
import com.bunbeauty.fooddelivery.domain.feature.cafe.model.cafe.PatchCafe
import com.bunbeauty.fooddelivery.domain.feature.cafe.model.cafe.PostCafe
import com.bunbeauty.fooddelivery.domain.toUuid

class CafeService(private val cafeRepository: CafeRepository) {

    suspend fun createCafe(userUuid: String, postCafe: PostCafe): GetCafe {
        // TODO get User/Company/Cities and check that postCafe/cityUuid is in this list
        return cafeRepository.insertCafe(
            insertCafe = postCafe.mapPostCafe()
        ).mapCafe()
    }

    suspend fun getCafeListByCityUuid(cityUuid: String): List<GetCafe> {
        return cafeRepository.getCafeListByCityUuid(cityUuid = cityUuid.toUuid())
            .map(mapCafe)
    }

    suspend fun updateCafe(userUuid: String, cafeUuid: String, patchCafe: PatchCafe): GetCafe? {
        // TODO get User/Company/Cities and check that cafe with cafeUuid is in this list
        return cafeRepository.updateCafe(
            cafeUuid = cafeUuid.toUuid(),
            updateCafe = patchCafe.mapPatchCafe()
        )?.mapCafe()
    }

}