package com.bunbeauty.fooddelivery.data.model.order.client.insert

import java.util.*

class InsertOrder(
    val time: Long,
    val isDelivery: Boolean,
    val code: String,
    val addressDescription: String,
    val comment: String?,
    val deferredTime: Long?,
    val status: String,
    val deliveryCost: Int?,
    val cafeUuid: UUID,
    val companyUuid: UUID,
    val clientUserUuid: UUID,
    val orderProductList: List<InsertOrderProduct>,
)