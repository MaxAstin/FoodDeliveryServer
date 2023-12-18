package com.bunbeauty.fooddelivery.domain.feature.cafe.model.deliveryzone

import java.util.*

class InsertDeliveryZone(
    val isVisible: Boolean,
    val cafeUuid: UUID,
    val points: List<InsertPoint>
)

class InsertPoint(
    var order: Int,
    var latitude: Double,
    var longitude: Double,
    val isVisible: Boolean,
)