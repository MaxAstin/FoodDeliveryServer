package com.bunbeauty.fooddelivery.domain.feature.order.model.v2.cafe

import com.bunbeauty.fooddelivery.domain.feature.order.model.GetOrderProduct
import com.bunbeauty.fooddelivery.domain.feature.order.model.v2.client.GetOrderAddressV2
import com.bunbeauty.fooddelivery.domain.model.client_user.GetCafeClientUser
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
    val address: GetOrderAddressV2,
    val comment: String?,
    val clientUser: GetCafeClientUser,
    val cafeUuid: String,
    val deliveryCost: Int?,
    val oldTotalCost: Int?,
    val newTotalCost: Int,
    val percentDiscount: Int?,
    val paymentMethod: String?,
    val oderProductList: List<GetOrderProduct>,
    val availableStatusList: List<String>
)
