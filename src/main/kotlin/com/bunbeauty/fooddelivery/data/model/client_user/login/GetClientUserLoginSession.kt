package com.bunbeauty.fooddelivery.data.model.client_user.login

data class GetClientUserLoginSession(
    val uuid: String,
    val phoneNumber: String,
    val time: Long,
    val code: String,
)