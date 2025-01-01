package com.bunbeauty.fooddelivery.domain.feature.cafe.model.deliveryzone

class DeliveryZone(
    val uuid: String,
    val name: String,
    val minOrderCost: Int?,
    val normalDeliveryCost: Int,
    val forLowDeliveryCost: Int?,
    val lowDeliveryCost: Int?,
    val isVisible: Boolean,
    val cafeUuid: String,
    val points: List<Point>
)

class Point(
    val uuid: String,
    var order: Int,
    var latitude: Double,
    var longitude: Double,
    val isVisible: Boolean,
)