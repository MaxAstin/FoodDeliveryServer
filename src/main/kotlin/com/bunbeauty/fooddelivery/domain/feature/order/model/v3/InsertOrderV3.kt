package com.bunbeauty.fooddelivery.domain.feature.order.model.v3

import com.bunbeauty.fooddelivery.domain.feature.order.model.v2.InsertOrderAddressV2

class InsertOrderV3(
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
    val orderProductList: List<InsertOrderProductV3>
)

class InsertOrderProductV3(
    val menuProductUuid: String,
    val count: Int,
    val additionUuids: List<String>
)
