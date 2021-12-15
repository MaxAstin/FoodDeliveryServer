package com.bunbeauty.fooddelivery.data.model.order

import kotlinx.serialization.Serializable

@Serializable
data class PatchOrder(
    val status: String,
)
