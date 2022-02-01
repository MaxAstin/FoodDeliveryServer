package com.bunbeauty.fooddelivery.data.model.city

import kotlinx.serialization.Serializable

@Serializable
data class GetCity(
    val uuid: String,
    val name: String,
    val timeZone: String,
    val isVisible: Boolean,
)