package com.bunbeauty.fooddelivery.data.model.order

import kotlinx.serialization.Serializable

@Serializable
data class GetClientOrder(
    val uuid: String,
    val code: String,
    val status: String,
    val time: Long,
    val isDelivery: Boolean,
    val deferredTime: Long?,
    val addressDescription: String,
    val comment: String?,
    val oderProductList: List<GetOrderProduct>,
)