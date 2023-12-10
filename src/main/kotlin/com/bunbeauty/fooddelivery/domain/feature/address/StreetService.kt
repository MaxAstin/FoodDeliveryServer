package com.bunbeauty.fooddelivery.domain.feature.address

import com.bunbeauty.fooddelivery.data.features.address.StreetRepository
import com.bunbeauty.fooddelivery.domain.feature.address.mapper.mapPostStreet
import com.bunbeauty.fooddelivery.domain.feature.address.mapper.mapStreet
import com.bunbeauty.fooddelivery.domain.model.street.GetStreet
import com.bunbeauty.fooddelivery.domain.model.street.PostStreet
import com.bunbeauty.fooddelivery.domain.toUuid

class StreetService(private val streetRepository: StreetRepository) {

    suspend fun createStreet(postStreet: PostStreet): GetStreet {
        return streetRepository.insertStreet(
            insertStreet = postStreet.mapPostStreet()
        ).mapStreet()
    }

    suspend fun getStreetListByCityUuid(cityUuid: String): List<GetStreet> {
        return streetRepository.getStreetListByCityUuid(cityUuid = cityUuid.toUuid())
            .map(mapStreet)
    }

}