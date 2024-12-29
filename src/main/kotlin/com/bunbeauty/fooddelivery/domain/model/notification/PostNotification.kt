package com.bunbeauty.fooddelivery.domain.model.notification

import kotlinx.serialization.Serializable

@Serializable
class PostNotification(
    val title: String,
    val body: String
)
