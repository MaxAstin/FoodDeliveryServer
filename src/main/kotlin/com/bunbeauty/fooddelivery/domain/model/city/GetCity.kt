package com.bunbeauty.fooddelivery.domain.model.city

import kotlinx.serialization.Serializable

@Serializable
class GetCity(
    val uuid: String,
    val name: String,
    val timeZone: String,
    val isVisible: Boolean
)
