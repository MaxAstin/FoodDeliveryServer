package com.bunbeauty.fooddelivery.domain.model.client_user.login

import kotlinx.serialization.Serializable

@Serializable
class GetTestClientUserPhone(
    val uuid: String,
    val phoneNumber: String,
    val code: String
)
