package com.bunbeauty.food_delivery.data.model.order

import kotlinx.serialization.Serializable

@Serializable
data class PatchOrder(
    val status: String,
)
