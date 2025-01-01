package com.bunbeauty.fooddelivery.domain.feature.user.model.domain

data class NotificationData(
    val token: String,
    val device: String?,
    val updateTokenDateTime: String
)
