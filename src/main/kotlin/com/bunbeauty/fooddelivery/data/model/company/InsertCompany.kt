package com.bunbeauty.fooddelivery.data.model.company

data class InsertCompany(
    val name: String,
    val forFreeDelivery: Int,
    val deliveryCost: Int,
    val forceUpdateVersion: Int,
)
