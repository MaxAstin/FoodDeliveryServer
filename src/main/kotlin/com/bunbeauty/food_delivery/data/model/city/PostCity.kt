package com.bunbeauty.food_delivery.data.model.city

import kotlinx.serialization.Serializable

@Serializable
data class PostCity(
    val name: String,
    val offset: Int,
    val companyUuid: String,
    val isVisible: Boolean,
)