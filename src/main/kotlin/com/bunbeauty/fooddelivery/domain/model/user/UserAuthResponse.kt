package com.bunbeauty.fooddelivery.domain.model.user

import kotlinx.serialization.Serializable

@Serializable
class UserAuthResponse(
    val token: String,
    val cafeUuid: String,
    val companyUuid: String
)
