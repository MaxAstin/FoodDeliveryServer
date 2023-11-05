package com.bunbeauty.fooddelivery.domain.model.order

import kotlinx.serialization.Serializable

@Serializable
class GetOrderAddress(
    val description: String?,
    val street: String?,
    val house: String?,
    val flat: String?,
    val entrance: String?,
    val floor: String?,
    val comment: String?,
)