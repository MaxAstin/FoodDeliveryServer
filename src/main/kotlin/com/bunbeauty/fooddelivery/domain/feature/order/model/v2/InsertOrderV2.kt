package com.bunbeauty.fooddelivery.domain.feature.order.model.v2

import com.bunbeauty.fooddelivery.domain.feature.order.model.v1.InsertOrderProduct

class InsertOrderV2(
    val time: Long,
    val isDelivery: Boolean,
    val code: String,
    val address: InsertOrderAddressV2,
    val comment: String?,
    val deferredTime: Long?,
    val status: String,
    val deliveryCost: Int?,
    val paymentMethod: String?,
    val percentDiscount: Int?,
    val cafeUuid: String,
    val companyUuid: String,
    val clientUserUuid: String,
    val orderProductList: List<InsertOrderProduct>
)

class InsertOrderAddressV2(
    val description: String?,
    val street: String?,
    val house: String?,
    val flat: String?,
    val entrance: String?,
    val floor: String?,
    val comment: String?
)
