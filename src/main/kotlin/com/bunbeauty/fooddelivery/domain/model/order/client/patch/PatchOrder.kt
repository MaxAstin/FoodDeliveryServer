package com.bunbeauty.fooddelivery.domain.model.order.client.patch

import kotlinx.serialization.Serializable

@Serializable
class PatchOrder(
    val status: String,
)