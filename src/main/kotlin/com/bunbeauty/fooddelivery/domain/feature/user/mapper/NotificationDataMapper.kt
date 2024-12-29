package com.bunbeauty.fooddelivery.domain.feature.user.mapper

import com.bunbeauty.fooddelivery.domain.feature.user.model.api.PutNotificationToken
import com.bunbeauty.fooddelivery.domain.feature.user.model.domain.NotificationData

fun PutNotificationToken.toNotificationData(dateTime: String): NotificationData {
    return NotificationData(
        token = token,
        device = device,
        updateTokenDateTime = dateTime
    )
}
