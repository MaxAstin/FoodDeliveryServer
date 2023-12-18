package com.bunbeauty.fooddelivery.domain.feature.cafe.model.deliveryzone

import kotlinx.serialization.Serializable

@Serializable
class PostDeliveryZone(
    val isVisible: Boolean,
    val cafeUuid: String,
    val points: List<PostPoint>
)

@Serializable
class PostPoint(
    var order: Int,
    var latitude: Double,
    var longitude: Double,
    val isVisible: Boolean,
)