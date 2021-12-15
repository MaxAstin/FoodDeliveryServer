package com.bunbeauty.fooddelivery.data.model.user

import kotlinx.serialization.Serializable

@Serializable
data class PostUserAuth(
    val username: String,
    val password: String,
)
