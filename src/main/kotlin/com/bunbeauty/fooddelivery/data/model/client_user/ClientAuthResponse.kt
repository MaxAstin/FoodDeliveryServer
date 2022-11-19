package com.bunbeauty.fooddelivery.data.model.client_user

import kotlinx.serialization.Serializable

@Serializable
data class ClientAuthResponse(
    val token: String,
    val userUuid: String
)
