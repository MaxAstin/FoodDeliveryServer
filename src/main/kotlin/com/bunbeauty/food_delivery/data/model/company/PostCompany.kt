package com.bunbeauty.food_delivery.data.model.company

import kotlinx.serialization.Serializable

@Serializable
data class PostCompany(
    val name: String,
    val forFreeDelivery: Int,
    val deliveryCost: Int,
)
