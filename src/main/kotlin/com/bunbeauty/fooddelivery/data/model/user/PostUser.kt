package com.bunbeauty.fooddelivery.data.model.user

import kotlinx.serialization.Serializable

@Serializable
class PostUser(
    val username: String,
    val password: String,
    val role: String,
    val cityUuid: String
)