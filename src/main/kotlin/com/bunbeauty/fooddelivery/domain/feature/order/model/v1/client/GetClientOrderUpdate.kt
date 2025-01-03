package com.bunbeauty.fooddelivery.domain.feature.order.model.v1.client

import kotlinx.serialization.Serializable

@Serializable
class GetClientOrderUpdate(
    val uuid: String,
    val status: String,
    val clientUserUuid: String
)
