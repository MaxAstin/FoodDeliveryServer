package com.bunbeauty.food_delivery.data.model.category

import kotlinx.serialization.Serializable

@Serializable
data class GetCategory(
    val uuid: String,
    val name: String,
)
