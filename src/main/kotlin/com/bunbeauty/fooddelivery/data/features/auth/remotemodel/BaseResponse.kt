package com.bunbeauty.fooddelivery.data.features.auth.remotemodel

import kotlinx.serialization.Serializable

@Serializable
class BaseResponse(
    val success: Boolean,
    val message: String?,
)