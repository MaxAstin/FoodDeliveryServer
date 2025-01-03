package com.bunbeauty.fooddelivery.fake

import com.bunbeauty.fooddelivery.domain.feature.cafe.model.deliveryzone.DeliveryZone

object FakeDeliveryZone {

    fun create(
        minOrderCost: Int? = null,
        normalDeliveryCost: Int = 0,
        forLowDeliveryCost: Int? = null,
        lowDeliveryCost: Int? = null
    ): DeliveryZone {
        return DeliveryZone(
            uuid = "",
            name = "",
            minOrderCost = minOrderCost,
            normalDeliveryCost = normalDeliveryCost,
            forLowDeliveryCost = forLowDeliveryCost,
            lowDeliveryCost = lowDeliveryCost,
            isVisible = true,
            cafeUuid = "",
            points = emptyList()
        )
    }
}
