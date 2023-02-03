package com.bunbeauty.fooddelivery.data.model.order

import kotlinx.serialization.Serializable

@Serializable
data class GetStatisticOrderProduct(
    val uuid: String,
    val count: Int,
    val newPrice: Int,
)