package com.bunbeauty.fooddelivery.domain.model.order.cafe

import com.bunbeauty.fooddelivery.domain.model.client_user.GetCafeClientUser
import com.bunbeauty.fooddelivery.domain.model.order.GetOrderAddress
import com.bunbeauty.fooddelivery.domain.model.order.GetOrderProduct
import kotlinx.serialization.Serializable

@Serializable
class GetCafeOrderDetailsV2(
    val uuid: String,
    val code: String,
    val status: String,
    val time: Long,
    val timeZone: String,
    val isDelivery: Boolean,
    val deferredTime: Long?,
    val address: GetOrderAddress,
    val comment: String?,
    val clientUser: GetCafeClientUser,
    val cafeUuid: String,
    val deliveryCost: Int?,
    val oldTotalCost: Int?,
    val newTotalCost: Int,
    val percentDiscount: Int?,
    val paymentMethod: String?,
    val oderProductList: List<GetOrderProduct>,
    val availableStatusList: List<String>,
)