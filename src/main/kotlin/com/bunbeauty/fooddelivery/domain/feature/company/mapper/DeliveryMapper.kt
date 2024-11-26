package com.bunbeauty.fooddelivery.domain.feature.company.mapper

import com.bunbeauty.fooddelivery.domain.feature.company.Delivery
import com.bunbeauty.fooddelivery.domain.model.company.delivery.GetDelivery

val mapDelivery: Delivery.() -> GetDelivery = {
    GetDelivery(
        forFree = forFree,
        cost = cost
    )
}