package com.bunbeauty.fooddelivery.domain.model.company.discount

import kotlinx.serialization.Serializable

@Serializable
class GetDiscount(
    val firstOrderDiscountPercent: Int?
)
