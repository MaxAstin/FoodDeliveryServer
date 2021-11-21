package com.bunbeauty.food_delivery.data.model.city

import kotlinx.serialization.Serializable

@Serializable
data class GetCity(
    val uuid: String,
    val name: String,
    val offset: Int,
    val isVisible: Boolean,
)