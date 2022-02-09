package com.bunbeauty.fooddelivery.data.model.client_user

import kotlinx.serialization.Serializable

@Serializable
class PostClientCode(
    val uuid: String,
    val code: String
)