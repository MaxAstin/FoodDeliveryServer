package com.bunbeauty.fooddelivery.data.model.client_user

import kotlinx.serialization.Serializable

class ClientAuthResponse(
    val token: String,
    val userUuid: String
)
