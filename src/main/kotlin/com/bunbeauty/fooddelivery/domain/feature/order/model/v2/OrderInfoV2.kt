package com.bunbeauty.fooddelivery.domain.feature.order.model.v2

class OrderInfoV2(
    val time: Long,
    val code: String,
    val deliveryCost: Int?,
    val percentDiscount: Int?,
    val cafeUuid: String,
    val companyUuid: String,
    val clientUserUuid: String,
)
