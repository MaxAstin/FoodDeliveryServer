package com.bunbeauty.fooddelivery.domain.feature.cafe.model.deliveryzone

import kotlinx.serialization.Serializable

@Serializable
class GetDeliveryZone(
    val uuid: String,
    val isVisible: Boolean,
    val cafeUuid: String,
    val points: List<GetPoint>
)

@Serializable
class GetPoint(
    val uuid: String,
    var order: Int,
    var latitude: Double,
    var longitude: Double,
    val isVisible: Boolean,
)