package com.bunbeauty.fooddelivery.domain.model.order.client.post

import kotlinx.serialization.Serializable

@Serializable
class PostOrder(
    val isDelivery: Boolean,
    val comment: String?,
    val addressDescription: String,
    val deferredTime: Long?,
    val addressUuid: String?,
    val cafeUuid: String?,
    val orderProducts: List<PostOrderProduct>,
)
