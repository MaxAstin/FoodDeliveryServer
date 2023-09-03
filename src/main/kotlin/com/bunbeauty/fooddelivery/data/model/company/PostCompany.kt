package com.bunbeauty.fooddelivery.data.model.company

import kotlinx.serialization.Serializable

@Serializable
class PostCompany(
    val name: String,
    val forFreeDelivery: Int,
    val deliveryCost: Int,
    val forceUpdateVersion: Int,
    val percentDiscount: Int?,
)
