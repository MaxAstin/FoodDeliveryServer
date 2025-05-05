package com.bunbeauty.fooddelivery.domain.feature.notification

import com.bunbeauty.fooddelivery.data.repo.ClientUserRepository
import com.bunbeauty.fooddelivery.domain.model.notification.PostNotification
import com.google.firebase.messaging.AndroidConfig
import com.google.firebase.messaging.FcmOptions
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification

private const val ANALYTICS_LABEL = "order"

class GetClientNotificationUseCase(
    private val userClientRepository: ClientUserRepository
) {
    suspend operator fun invoke(clientUuid: String, postNotification: PostNotification): Message? {
        val notificationToken =
            userClientRepository.getClientByUuid(uuid = clientUuid)?.notificationToken ?: return null

        return Message.builder()
            .setAndroidConfig(
                AndroidConfig.builder()
                    .setPriority(AndroidConfig.Priority.HIGH)
                    .build()
            )
            .setNotification(
                Notification.builder()
                    .setTitle(postNotification.title)
                    .setBody(postNotification.body)
                    .build()
            )
            .setToken(notificationToken)
            .setFcmOptions(
                FcmOptions.withAnalyticsLabel(ANALYTICS_LABEL)
            )
            .build()
    }
}
