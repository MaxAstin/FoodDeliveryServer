package com.bunbeauty.fooddelivery.domain.model.street

import kotlinx.serialization.Serializable

@Serializable
class PostStreet(
    val name: String,
    val cafeUuid: String,
    val isVisible: Boolean,
)
