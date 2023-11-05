package com.bunbeauty.fooddelivery.domain.model.order.client.get

import kotlinx.serialization.Serializable

@Serializable
class GetClientOrderUpdate(
    val uuid: String,
    val status: String,
    val clientUserUuid: String
)