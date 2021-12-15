package com.bunbeauty.fooddelivery.data.model.delivery

import kotlinx.serialization.Serializable

@Serializable
data class GetDelivery(
    val forFree: Int,
    val cost: Int
)
