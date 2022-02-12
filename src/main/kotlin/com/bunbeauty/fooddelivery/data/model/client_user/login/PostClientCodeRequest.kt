package com.bunbeauty.fooddelivery.data.model.client_user.login

import kotlinx.serialization.Serializable

@Serializable
class PostClientCodeRequest(
    val phoneNumber: String
)