package com.bunbeauty.fooddelivery.domain.feature.cafe.model.cafe

import com.bunbeauty.fooddelivery.domain.feature.cafe.model.deliveryzone.DeliveryZone

class Cafe(
    val uuid: String,
    val fromTime: Int,
    val toTime: Int,
    val offset: Int,
    val phone: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val cityUuid: String,
    val isVisible: Boolean,
    val zones: List<DeliveryZone>
)
