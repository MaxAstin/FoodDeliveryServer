package com.bunbeauty.fooddelivery.data.model.client_user.login

data class InsertClientUserLoginSession(
    val phoneNumber: String,
    val time: Long,
    val code: String
)
