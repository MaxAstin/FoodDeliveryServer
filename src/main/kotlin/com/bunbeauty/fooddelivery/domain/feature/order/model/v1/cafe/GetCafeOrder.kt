package com.bunbeauty.fooddelivery.domain.feature.order.model.v1.cafe

import kotlinx.serialization.Serializable

@Serializable
class GetCafeOrder(
    val uuid: String,
    val code: String,
    val status: String,
    val time: Long,
    val timeZone: String,
    val deferredTime: Long?,
    val cafeUuid: String,
)