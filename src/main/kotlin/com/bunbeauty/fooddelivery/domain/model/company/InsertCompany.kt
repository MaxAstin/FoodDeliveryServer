package com.bunbeauty.fooddelivery.domain.model.company

class InsertCompany(
    val name: String,
    val forFreeDelivery: Int,
    val deliveryCost: Int,
    val forceUpdateVersion: Int,
    val percentDiscount: Int?,
)
