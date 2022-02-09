package com.bunbeauty.fooddelivery.data.model.client_user

import kotlinx.serialization.Serializable

@Serializable
class PostClientCodeRequest(
    val phone: String
)