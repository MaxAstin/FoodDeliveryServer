package com.bunbeauty.fooddelivery.domain.feature.user.model.api

import kotlinx.serialization.Serializable

@Serializable
class PutNotificationToken(
    val token: String,
    val device: String? = null,
)