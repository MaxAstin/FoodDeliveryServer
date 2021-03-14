package com.bunbeauty.fooddelivery.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ClientAuthResponse(
    val token: String
)
