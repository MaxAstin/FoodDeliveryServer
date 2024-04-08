package com.bunbeauty.fooddelivery.domain.feature.order.model.v1

import kotlinx.serialization.Serializable

@Serializable
class PatchOrder(
    val status: String,
)