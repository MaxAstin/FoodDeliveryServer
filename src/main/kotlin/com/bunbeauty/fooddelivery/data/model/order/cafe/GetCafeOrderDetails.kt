package com.bunbeauty.fooddelivery.data.model.order.cafe

import com.bunbeauty.fooddelivery.data.model.client_user.GetCafeClientUser
import com.bunbeauty.fooddelivery.data.model.order.GetOrderProduct
import kotlinx.serialization.Serializable

@Serializable
class GetCafeOrderDetails(
    val uuid: String,
    val code: String,
    val status: String,
    val time: Long,
    val timeZone: String,
    val isDelivery: Boolean,
    val deferredTime: Long?,
    val addressDescription: String,
    val comment: String?,
    val clientUser: GetCafeClientUser,
    val cafeUuid: String,
    val deliveryCost: Int?,
    val oldTotalCost: Int?,
    val newTotalCost: Int,
    val oderProductList: List<GetOrderProduct>,
    val availableStatusList: List<String>
)