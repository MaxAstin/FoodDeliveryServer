package com.bunbeauty.fooddelivery.data.model.order.client.patch

import kotlinx.serialization.Serializable

@Serializable
data class PatchOrder(
    val status: String,
)