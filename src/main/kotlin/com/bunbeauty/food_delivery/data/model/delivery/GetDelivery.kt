package com.bunbeauty.food_delivery.data.model.delivery

import kotlinx.serialization.Serializable

@Serializable
data class GetDelivery(
    val forFree: Int,
    val cost: Int
)
