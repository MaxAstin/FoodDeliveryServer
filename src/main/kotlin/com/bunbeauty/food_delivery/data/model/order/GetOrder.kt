package com.bunbeauty.food_delivery.data.model.order

import kotlinx.serialization.Serializable

@Serializable
data class GetOrder(
    val uuid: String,
    val isDelivery: Boolean,
    val code: String,
    val address: String,
    val comment: String?,
    val deferredTime: Long?,
    val status: String,
    val addressUuid: String?,
    val cafeUuid: String,
    val userUuid: String,
)
