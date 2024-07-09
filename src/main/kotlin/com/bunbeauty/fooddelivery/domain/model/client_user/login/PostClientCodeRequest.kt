package com.bunbeauty.fooddelivery.domain.model.client_user.login

import kotlinx.serialization.Serializable

@Serializable
class PostClientCodeRequest(
    val phoneNumber: String
)