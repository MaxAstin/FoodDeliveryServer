package com.bunbeauty.fooddelivery.domain.model.user

import kotlinx.serialization.Serializable

@Serializable
class GetUser(
    val uuid: String,
    val username: String,
    val passwordHash: String,
    val role: String,
    val unlimitedNotification: Boolean
)
