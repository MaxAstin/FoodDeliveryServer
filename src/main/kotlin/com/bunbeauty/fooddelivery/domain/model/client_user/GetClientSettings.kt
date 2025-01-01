package com.bunbeauty.fooddelivery.domain.model.client_user

import kotlinx.serialization.Serializable

@Serializable
class GetClientSettings(
    val uuid: String,
    val phoneNumber: String,
    val email: String?,
    val isActive: Boolean,
)