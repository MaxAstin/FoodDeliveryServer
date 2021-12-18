package com.bunbeauty.fooddelivery.data.model.order

import com.bunbeauty.fooddelivery.data.model.client_user.GetCafeClientUser
import kotlinx.serialization.Serializable

@Serializable
data class GetCafeOrder(
    val uuid: String,
    val code: String,
    val status: String,
    val time: Long,
    val isDelivery: Boolean,
    val deferredTime: Long?,
    val addressDescription: String,
    val comment: String?,
    val clientUser: GetCafeClientUser,
    val cafeUuid: String,
    val oderProductList: List<GetOrderProduct>,
)