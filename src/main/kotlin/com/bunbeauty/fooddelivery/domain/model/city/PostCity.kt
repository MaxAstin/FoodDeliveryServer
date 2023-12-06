package com.bunbeauty.fooddelivery.domain.model.city

import kotlinx.serialization.Serializable

@Serializable
class PostCity(
    val name: String,
    val timeZone: String,
    val companyUuid: String,
    val isVisible: Boolean,
)