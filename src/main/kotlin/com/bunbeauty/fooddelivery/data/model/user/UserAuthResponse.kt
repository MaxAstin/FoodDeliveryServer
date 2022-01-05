package com.bunbeauty.fooddelivery.data.model.user

import kotlinx.serialization.Serializable

@Serializable
data class UserAuthResponse(
    val token: String,
    val cityUuid: String,
    val companyUuid: String,
)
