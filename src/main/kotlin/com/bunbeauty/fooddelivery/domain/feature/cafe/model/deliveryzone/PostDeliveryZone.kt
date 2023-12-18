package com.bunbeauty.fooddelivery.domain.feature.cafe.model.deliveryzone

class PostDeliveryZone(
    val isVisible: Boolean,
    val cafeUuid: String,
    val points: List<PostPoint>
)

class PostPoint(
    var order: Int,
    var latitude: Double,
    var longitude: Double,
    val isVisible: Boolean,
)