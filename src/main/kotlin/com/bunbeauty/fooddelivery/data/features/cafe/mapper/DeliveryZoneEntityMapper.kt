package com.bunbeauty.fooddelivery.data.features.cafe.mapper

import com.bunbeauty.fooddelivery.data.entity.cafe.DeliveryZoneEntity
import com.bunbeauty.fooddelivery.data.entity.cafe.DeliveryZonePointEntity
import com.bunbeauty.fooddelivery.domain.feature.cafe.model.deliveryzone.DeliveryZone
import com.bunbeauty.fooddelivery.domain.feature.cafe.model.deliveryzone.Point

val mapDeliveryZoneEntity: DeliveryZoneEntity.() -> DeliveryZone = {
    DeliveryZone(
        uuid = uuid,
        name = name,
        minOrderCost = minOrderCost,
        normalDeliveryCost = normalDeliveryCost,
        forLowDeliveryCost = forLowDeliveryCost,
        lowDeliveryCost = lowDeliveryCost,
        isVisible = isVisible,
        cafeUuid = cafe.uuid,
        points = points.map(mapDeliveryZonePointEntity)
    )
}

val mapDeliveryZonePointEntity: DeliveryZonePointEntity.() -> Point = {
    Point(
        uuid = uuid,
        order = order,
        latitude = latitude,
        longitude = longitude,
        isVisible = isVisible
    )
}
