package com.bunbeauty.fooddelivery.domain.model.company.delivery

import kotlinx.serialization.Serializable

@Serializable
class GetDelivery(
    val forFree: Int,
    val cost: Int
)
