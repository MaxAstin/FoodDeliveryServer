package com.bunbeauty.fooddelivery.data.model.client_user.login

import kotlinx.serialization.Serializable

@Serializable
data class GetTestClientUserPhone(
    val uuid: String,
    val phoneNumber: String,
    val code: String
)
