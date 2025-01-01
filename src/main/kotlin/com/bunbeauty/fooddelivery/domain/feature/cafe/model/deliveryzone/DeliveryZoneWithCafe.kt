package com.bunbeauty.fooddelivery.domain.feature.cafe.model.deliveryzone

import com.bunbeauty.fooddelivery.domain.feature.cafe.model.cafe.Cafe

data class DeliveryZoneWithCafe(
    val deliveryZone: DeliveryZone,
    val cafe: Cafe
)