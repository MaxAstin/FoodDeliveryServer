package com.bunbeauty.fooddelivery.domain.feature.order.model.v1

class OrderInfo(
    val time: Long,
    val code: String,
    val deliveryCost: Int?,
    val cafeUuid: String,
    val companyUuid: String,
    val clientUserUuid: String,
)
