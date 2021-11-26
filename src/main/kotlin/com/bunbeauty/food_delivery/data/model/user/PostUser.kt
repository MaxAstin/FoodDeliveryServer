package com.bunbeauty.food_delivery.data.model.user

import kotlinx.serialization.Serializable

@Serializable
data class PostUser(
    val username: String,
    val password: String,
    val role: String,
    val companyUuid: String,
)