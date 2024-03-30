package com.bunbeauty.fooddelivery.domain.feature.cafe.mapper.deliveryzone

import com.bunbeauty.fooddelivery.domain.feature.cafe.model.deliveryzone.InsertDeliveryZone
import com.bunbeauty.fooddelivery.domain.feature.cafe.model.deliveryzone.InsertPoint
import com.bunbeauty.fooddelivery.domain.feature.cafe.model.deliveryzone.PostDeliveryZone
import com.bunbeauty.fooddelivery.domain.feature.cafe.model.deliveryzone.PostPoint
import com.bunbeauty.fooddelivery.domain.toUuid

val mapPostDeliveryZone: PostDeliveryZone.() -> InsertDeliveryZone = {
    InsertDeliveryZone(
        minOrderCost = minOrderCost,
        normalDeliveryCost = normalDeliveryCost,
        forLowDeliveryCost = forLowDeliveryCost,
        lowDeliveryCost = lowDeliveryCost,
        isVisible = isVisible,
        cafeUuid = cafeUuid.toUuid(),
        points = points.map(mapPostPoint)
    )
}

val mapPostPoint: PostPoint.() -> InsertPoint = {
    InsertPoint(
        order = order,
        latitude = latitude,
        longitude = longitude,
        isVisible = isVisible,
    )
}