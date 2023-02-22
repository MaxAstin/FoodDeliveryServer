package com.bunbeauty.fooddelivery.data.model.client_user.login

import kotlinx.serialization.Serializable

@Serializable
class InsertTestClientUserPhone(
    val phoneNumber: String,
    val code: String
)
