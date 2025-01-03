package com.bunbeauty.fooddelivery.domain.feature.order.model.v2.client

import com.bunbeauty.fooddelivery.domain.feature.order.model.GetOrderProduct
import kotlinx.serialization.Serializable

@Serializable
class GetClientOrderV2(
    val uuid: String,
    val code: String,
    val status: String,
    val time: Long,
    val timeZone: String,
    val isDelivery: Boolean,
    val deferredTime: Long?,
    val address: GetOrderAddressV2,
    val comment: String?,
    val deliveryCost: Int?,
    val oldTotalCost: Int?,
    val newTotalCost: Int,
    val paymentMethod: String?,
    val percentDiscount: Int?,
    val clientUserUuid: String,
    val oderProductList: List<GetOrderProduct>
)

@Serializable
class GetOrderAddressV2(
    val description: String?,
    val street: String?,
    val house: String?,
    val flat: String?,
    val entrance: String?,
    val floor: String?,
    val comment: String?
)
