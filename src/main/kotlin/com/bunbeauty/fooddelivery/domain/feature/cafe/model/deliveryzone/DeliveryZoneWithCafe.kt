package com.bunbeauty.fooddelivery.domain.feature.cafe.model.deliveryzone

import com.bunbeauty.fooddelivery.domain.feature.cafe.model.cafe.CafeWithZones

data class DeliveryZoneWithCafe(
    val deliveryZone: DeliveryZone,
    val cafeWithZones: CafeWithZones
)
