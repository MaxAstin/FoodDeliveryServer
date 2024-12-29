package com.bunbeauty.fooddelivery.domain.feature.order.model

import kotlinx.serialization.Serializable

@Serializable
class OrderAvailability(
    val isAvailable: Boolean
)
