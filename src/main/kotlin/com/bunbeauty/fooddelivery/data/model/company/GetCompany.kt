package com.bunbeauty.fooddelivery.data.model.company

import com.bunbeauty.fooddelivery.data.model.delivery.GetDelivery
import kotlinx.serialization.Serializable

@Serializable
class GetCompany(
    val uuid: String,
    val name: String,
    val offset: Int,
    val delivery: GetDelivery,
    val forceUpdateVersion: GetForceUpdateVersion,
    val payment: GetPayment
)