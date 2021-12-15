package com.bunbeauty.fooddelivery.data.model.street

import kotlinx.serialization.Serializable

@Serializable
data class PostStreet(
    val name: String,
    val cafeUuid: String,
    val isVisible: Boolean,
)
