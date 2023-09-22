package com.bunbeauty.fooddelivery.data.model.client_user.login

import kotlinx.serialization.Serializable

@Serializable
class GetAuthSession(
    val uuid: String,
    val phoneNumber: String,
    val time: Long,
    val isConfirmed: Boolean,
    val companyUuid: String,
)