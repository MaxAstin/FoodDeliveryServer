package com.bunbeauty.fooddelivery.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserAuthResponse(
    val token: String,
    val cityUuid: String
)
