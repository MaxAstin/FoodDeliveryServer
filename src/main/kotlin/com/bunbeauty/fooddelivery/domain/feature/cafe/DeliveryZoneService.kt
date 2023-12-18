package com.bunbeauty.fooddelivery.domain.feature.cafe

import com.bunbeauty.fooddelivery.data.features.cafe.DeliveryZoneRepository
import com.bunbeauty.fooddelivery.domain.feature.cafe.mapper.deliveryzone.mapDeliveryZone
import com.bunbeauty.fooddelivery.domain.feature.cafe.mapper.deliveryzone.mapPostDeliveryZone
import com.bunbeauty.fooddelivery.domain.feature.cafe.model.deliveryzone.GetDeliveryZone
import com.bunbeauty.fooddelivery.domain.feature.cafe.model.deliveryzone.PostDeliveryZone
import com.bunbeauty.fooddelivery.domain.toUuid

class DeliveryZoneService(
    private val deliveryZoneRepository: DeliveryZoneRepository,
) {

    suspend fun createDeliveryZone(userUuid: String, postDeliveryZone: PostDeliveryZone): GetDeliveryZone {
        // TODO get User/Company/Cities and check that postDeliveryZone/cafeUuid is in this list
        return deliveryZoneRepository.insertDeliveryZone(
            insertDeliveryZone = postDeliveryZone.mapPostDeliveryZone()
        ).mapDeliveryZone()
    }

    suspend fun getDeliveryZoneListByCafeUuid(userUuid: String, cafeUuid: String): List<GetDeliveryZone> {
        return deliveryZoneRepository.getDeliveryZoneListByCafeUuid(
            cafeUuid = cafeUuid.toUuid()
        ).map(mapDeliveryZone)
    }

}