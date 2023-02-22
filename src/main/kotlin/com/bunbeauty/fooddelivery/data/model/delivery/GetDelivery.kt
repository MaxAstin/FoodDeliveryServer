package com.bunbeauty.fooddelivery.data.model.delivery

import kotlinx.serialization.Serializable

@Serializable
class GetDelivery(
    val forFree: Int,
    val cost: Int
)
