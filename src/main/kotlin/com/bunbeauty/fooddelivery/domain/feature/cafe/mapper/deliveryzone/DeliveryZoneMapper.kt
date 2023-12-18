package com.bunbeauty.fooddelivery.domain.feature.cafe.mapper.deliveryzone

import com.bunbeauty.fooddelivery.domain.feature.cafe.model.deliveryzone.DeliveryZone
import com.bunbeauty.fooddelivery.domain.feature.cafe.model.deliveryzone.GetDeliveryZone
import com.bunbeauty.fooddelivery.domain.feature.cafe.model.deliveryzone.GetPoint
import com.bunbeauty.fooddelivery.domain.feature.cafe.model.deliveryzone.Point

val mapDeliveryZone: DeliveryZone.() -> GetDeliveryZone = {
    GetDeliveryZone(
        uuid = uuid,
        isVisible = isVisible,
        cafeUuid = cafeUuid,
        points = points.map(mapPoint)
    )
}

val mapPoint: Point.() -> GetPoint = {
    GetPoint(
        uuid = uuid,
        order = order,
        latitude = latitude,
        longitude = longitude,
        isVisible = isVisible,
    )
}