package com.bunbeauty.fooddelivery.domain.feature.order.model

class LightOrder(
    val uuid: String,
    val code: String,
    val status: String,
    val time: Long,
    val timeZone: String,
    val deferredTime: Long?,
    val cafeUuid: String
)
