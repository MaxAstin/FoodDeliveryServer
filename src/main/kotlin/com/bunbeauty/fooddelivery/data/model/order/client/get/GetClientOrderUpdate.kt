package com.bunbeauty.fooddelivery.data.model.order.client.get

import kotlinx.serialization.Serializable

@Serializable
class GetClientOrderUpdate(
    val uuid: String,
    val status: String,
    val clientUserUuid: String
)