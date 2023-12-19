package com.bunbeauty.fooddelivery.domain.feature.cafe

import com.bunbeauty.fooddelivery.data.features.cafe.DeliveryZoneRepository
import com.bunbeauty.fooddelivery.domain.feature.cafe.mapper.deliveryzone.mapDeliveryZone
import com.bunbeauty.fooddelivery.domain.feature.cafe.mapper.deliveryzone.mapPostDeliveryZone
import com.bunbeauty.fooddelivery.domain.feature.cafe.model.deliveryzone.GetDeliveryZone
import com.bunbeauty.fooddelivery.domain.feature.cafe.model.deliveryzone.PostDeliveryZone
import com.bunbeauty.fooddelivery.domain.feature.privacy.PrivacyCheckService
import com.bunbeauty.fooddelivery.domain.toUuid

class DeliveryZoneService(
    private val deliveryZoneRepository: DeliveryZoneRepository,
    private val privacyCheckService: PrivacyCheckService,
) {

    suspend fun createDeliveryZone(userUuid: String, postDeliveryZone: PostDeliveryZone): GetDeliveryZone {
        privacyCheckService.checkIsCafeAvailable(
            userUuid = userUuid,
            cafeUuid = postDeliveryZone.cafeUuid
        )

        return deliveryZoneRepository.insertDeliveryZone(
            insertDeliveryZone = postDeliveryZone.mapPostDeliveryZone()
        ).mapDeliveryZone()
    }

    suspend fun getDeliveryZoneListByCafeUuid(userUuid: String, cafeUuid: String): List<GetDeliveryZone> {
        privacyCheckService.checkIsCafeAvailable(
            userUuid = userUuid,
            cafeUuid = cafeUuid
        )

        return deliveryZoneRepository.getDeliveryZoneListByCafeUuid(
            cafeUuid = cafeUuid.toUuid()
        ).map(mapDeliveryZone)
    }

}