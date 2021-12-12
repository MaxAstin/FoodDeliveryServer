package com.bunbeauty.food_delivery.data.model.order

import kotlinx.serialization.Serializable

@Serializable
data class PostOrder(
    val isDelivery: Boolean,
    val comment: String?,
    val addressDescription: String,
    val deferredTime: Long?,
    val addressUuid: String?,
    val cafeUuid: String?,
    val orderProducts: List<PostOrderProduct>,
)
