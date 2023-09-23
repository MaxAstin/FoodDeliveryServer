package com.bunbeauty.fooddelivery.data.model.client_user.login

import kotlinx.serialization.Serializable

@Serializable
class GetClientAuthSession(
    val uuid: String,
    val phoneNumber: String,
    val time: Long,
    val attemptsLeft: Int,
    val isConfirmed: Boolean,
    val companyUuid: String,
)