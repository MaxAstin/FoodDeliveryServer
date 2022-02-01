package com.bunbeauty.fooddelivery.data.model.city

import kotlinx.serialization.Serializable

@Serializable
data class PostCity(
    val name: String,
    val timeZone: String,
    val companyUuid: String,
    val isVisible: Boolean,
)