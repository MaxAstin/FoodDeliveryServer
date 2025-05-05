package com.bunbeauty.fooddelivery.domain.feature.notification

import com.bunbeauty.fooddelivery.domain.feature.user.model.domain.User
import com.google.firebase.messaging.AndroidConfig
import com.google.firebase.messaging.FcmOptions
import com.google.firebase.messaging.Message

private const val ORDER_CODE_KEY = "orderCode"
private const val UNLIMITED_KEY = "unlimited"
private const val ANALYTICS_LABEL = "order"

class GetOrderNotificationUseCase {
    operator fun invoke(user: User, orderCode: String): Message? {
        val notificationToken = user.notificationToken ?: return null

        return Message.builder()
            .setAndroidConfig(
                AndroidConfig.builder()
                    .setPriority(AndroidConfig.Priority.HIGH)
                    .build()
            )
            .setToken(notificationToken)
            .putAllData(
                mapOf(
                    ORDER_CODE_KEY to orderCode,
                    UNLIMITED_KEY to user.unlimitedNotification.toString()
                )
            )
            .setFcmOptions(
                FcmOptions.withAnalyticsLabel(ANALYTICS_LABEL)
            )
            .build()
    }
}
