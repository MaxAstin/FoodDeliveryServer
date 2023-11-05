package com.bunbeauty.fooddelivery.domain.model.auth

import kotlinx.serialization.Serializable

@Serializable
class BaseResponse(
    val success: Boolean,
    val message: String?,
)