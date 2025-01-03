package com.bunbeauty.fooddelivery.domain.model.client_user

import kotlinx.serialization.Serializable

@Serializable
class ClientAuthResponse(
    val token: String,
    val userUuid: String
)
