package com.bunbeauty.food_delivery.data.model.order

data class PostOrder(
    val isDelivery: Boolean,
    val comment: String?,
    val address: String,
    val deferredTime: Long?,
    val addressUuid: String?,
    val cafeUuid: String?,
    val orderProducts: List<PostOrderProduct>,
)
