package com.bunbeauty.fooddelivery.domain.model.client_user

import kotlinx.serialization.Serializable

@Serializable
class GetCafeClientUser(
    val uuid: String,
    val phoneNumber: String,
    val email: String?,
)