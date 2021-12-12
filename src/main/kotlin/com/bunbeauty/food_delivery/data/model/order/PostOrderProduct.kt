package com.bunbeauty.food_delivery.data.model.order

import kotlinx.serialization.Serializable

@Serializable
data class PostOrderProduct(
    val menuProductUuid: String,
    val count: Int,
)
