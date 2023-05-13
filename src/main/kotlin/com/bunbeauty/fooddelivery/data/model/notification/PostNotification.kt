package com.bunbeauty.fooddelivery.data.model.notification

import kotlinx.serialization.Serializable

@Serializable
class PostNotification(
    val title: String,
    val body: String,
)
