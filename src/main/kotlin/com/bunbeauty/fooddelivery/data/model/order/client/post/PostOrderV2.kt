package com.bunbeauty.fooddelivery.data.model.order.client.post

import kotlinx.serialization.Serializable

@Serializable
class PostOrderV2(
    val isDelivery: Boolean,
    val address: PostOrderAddress,
    val comment: String?,
    val deferredTime: Long?,
    val orderProducts: List<PostOrderProduct>,
)