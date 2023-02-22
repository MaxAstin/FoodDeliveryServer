package com.bunbeauty.fooddelivery.data.model.user

import kotlinx.serialization.Serializable

@Serializable
class UserAuthResponse(
    val token: String,
    val cityUuid: String,
    val companyUuid: String,
)
