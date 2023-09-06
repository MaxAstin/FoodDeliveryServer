package com.bunbeauty.fooddelivery.data.model.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class SendSmsResponse(
    @SerialName("success")
    val success: Boolean,
    @SerialName("message")
    val message: String,
)