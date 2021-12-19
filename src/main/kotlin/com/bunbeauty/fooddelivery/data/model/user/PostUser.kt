package com.bunbeauty.fooddelivery.data.model.user

import kotlinx.serialization.Serializable

@Serializable
data class PostUser(
    val username: String,
    val password: String,
    val role: String,
    val cityUuid: String,
)