package com.bunbeauty.fooddelivery.data.model.client_user.login

import kotlinx.serialization.Serializable

@Serializable
data class GetClientUserLoginSessionUuid(
    val uuid: String
)