package com.bunbeauty.fooddelivery.data.model.company

import kotlinx.serialization.Serializable

@Serializable
class GetDiscount(
    val firstOrderDiscountPercent: Int?
)
