package com.bunbeauty.fooddelivery.data.model.user

import kotlinx.serialization.Serializable

@Serializable
class PostUserAuth(
    val username: String,
    val password: String,
)
