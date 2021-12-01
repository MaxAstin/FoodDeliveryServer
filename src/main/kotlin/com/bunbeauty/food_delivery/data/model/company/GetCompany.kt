package com.bunbeauty.food_delivery.data.model.company

import com.bunbeauty.food_delivery.data.model.delivery.GetDelivery
import kotlinx.serialization.Serializable

@Serializable
data class GetCompany(
    val uuid: String,
    val name: String,
    val delivery: GetDelivery,
)