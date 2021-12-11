package com.bunbeauty.food_delivery.data.model.user

import kotlinx.serialization.Serializable

@Serializable
data class PostUserAuth(
    val username: String,
    val password: String,
)
