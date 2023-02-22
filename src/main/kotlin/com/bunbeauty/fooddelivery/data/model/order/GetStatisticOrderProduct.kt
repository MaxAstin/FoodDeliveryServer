package com.bunbeauty.fooddelivery.data.model.order

import kotlinx.serialization.Serializable

@Serializable
class GetStatisticOrderProduct(
    val uuid: String,
    val count: Int,
    val newPrice: Int,
)