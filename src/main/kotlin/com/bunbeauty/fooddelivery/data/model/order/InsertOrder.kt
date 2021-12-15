package com.bunbeauty.fooddelivery.data.model.order

import java.util.*

data class InsertOrder(
    val time: Long,
    val isDelivery: Boolean,
    val code: String,
    val addressDescription: String,
    val comment: String?,
    val deferredTime: Long?,
    val status: String,
    val cafeUuid: UUID,
    val clientUserUuid: UUID,
    val orderProductList: List<InsertOrderProduct>,
)
