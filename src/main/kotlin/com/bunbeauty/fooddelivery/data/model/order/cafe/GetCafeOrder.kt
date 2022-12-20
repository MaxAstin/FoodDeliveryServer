package com.bunbeauty.fooddelivery.data.model.order.cafe

import kotlinx.serialization.Serializable

@Serializable
data class GetCafeOrder(
    val uuid: String,
    val code: String,
    val status: String,
    val time: Long,
    val timeZone: String,
    val deferredTime: Long?,
    val cafeUuid: String,
)