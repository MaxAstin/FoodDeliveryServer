package com.bunbeauty.fooddelivery.data.model.order.client.post

import kotlinx.serialization.Serializable

@Serializable
class PostOrderProduct(
    val menuProductUuid: String,
    val count: Int,
)
