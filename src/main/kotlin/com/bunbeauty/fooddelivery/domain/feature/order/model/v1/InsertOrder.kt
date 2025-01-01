package com.bunbeauty.fooddelivery.domain.feature.order.model.v1

class InsertOrder(
    val time: Long,
    val isDelivery: Boolean,
    val code: String,
    val addressDescription: String,
    val comment: String?,
    val deferredTime: Long?,
    val status: String,
    val deliveryCost: Int?,
    val cafeUuid: String,
    val companyUuid: String,
    val clientUserUuid: String,
    val orderProductList: List<InsertOrderProduct>,
)

class InsertOrderProduct(
    val menuProductUuid: String,
    val count: Int,
)