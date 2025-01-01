package com.bunbeauty.fooddelivery.domain.feature.order.model.v1.client

import com.bunbeauty.fooddelivery.domain.feature.order.model.GetOrderProduct
import kotlinx.serialization.Serializable

@Serializable
class GetClientOrder(
    val uuid: String,
    val code: String,
    val status: String,
    val time: Long,
    val timeZone: String,
    val isDelivery: Boolean,
    val deferredTime: Long?,
    val addressDescription: String,
    val comment: String?,
    val deliveryCost: Int?,
    val oldTotalCost: Int?,
    val newTotalCost: Int,
    val clientUserUuid: String,
    val oderProductList: List<GetOrderProduct>
)
