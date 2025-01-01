package com.bunbeauty.fooddelivery.domain.feature.order.model.v1.cafe

import com.bunbeauty.fooddelivery.domain.feature.order.model.GetOrderProduct
import com.bunbeauty.fooddelivery.domain.model.client_user.GetCafeClientUser
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