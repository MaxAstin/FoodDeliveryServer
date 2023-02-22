package com.bunbeauty.fooddelivery.data.model.order.client.patch

import kotlinx.serialization.Serializable

@Serializable
class PatchOrder(
    val status: String,
)