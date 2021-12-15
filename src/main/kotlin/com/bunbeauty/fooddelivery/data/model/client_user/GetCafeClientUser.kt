package com.bunbeauty.fooddelivery.data.model.client_user

import kotlinx.serialization.Serializable

@Serializable
data class GetCafeClientUser(
    val uuid: String,
    val phoneNumber: String,
    val email: String?,
)