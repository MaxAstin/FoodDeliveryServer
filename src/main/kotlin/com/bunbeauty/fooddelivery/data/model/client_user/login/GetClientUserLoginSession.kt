package com.bunbeauty.fooddelivery.data.model.client_user.login

import kotlinx.serialization.Serializable

@Serializable
data class GetClientUserLoginSession(
    val uuid: String,
    val phoneNumber: String,
    val time: Long,
    val code: String,
)