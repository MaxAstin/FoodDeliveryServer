package com.bunbeauty.fooddelivery.domain.model.client_user.login

import kotlinx.serialization.Serializable

@Serializable
class InsertTestClientUserPhone(
    val phoneNumber: String,
    val code: String
)
