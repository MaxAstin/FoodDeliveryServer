package com.bunbeauty.fooddelivery.data.model.company

import com.bunbeauty.fooddelivery.data.model.delivery.GetDelivery
import kotlinx.serialization.Serializable

@Serializable
data class GetCompany(
    val uuid: String,
    val name: String,
    val delivery: GetDelivery,
    val forceUpdateVersion: GetForceUpdateVersion,
)