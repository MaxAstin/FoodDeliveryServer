package com.bunbeauty.fooddelivery.domain.feature.user

import kotlinx.serialization.Serializable

@Serializable
class PutNotificationToken(
    val token: String
)