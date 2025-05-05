package com.bunbeauty.fooddelivery.domain.feature.notification

import com.bunbeauty.fooddelivery.data.repo.ClientUserRepository
import com.google.firebase.messaging.AndroidConfig
import com.google.firebase.messaging.FcmOptions
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification

private const val ANALYTICS_LABEL = "client_pickup"
private const val TITLE = "Ваш заказ готов"

class SendPickupClientNotificationUseCase(
    private val userClientRepository: ClientUserRepository,
    private val firebaseMessaging: FirebaseMessaging
) {
    suspend operator fun invoke(clientUuid: String, orderCode: String) {
        try {
            sendMessage(
                clientUuid = clientUuid,
                orderCode = orderCode
            )?.let(firebaseMessaging::send)
            println("sendPickupNotification success clientUuid $clientUuid ")
        } catch (exception: Exception) {
            println("sendPickupNotification exception:")
            exception.printStackTrace()
        }
    }
    private suspend fun sendMessage(
        clientUuid: String,
        orderCode: String
    ): Message? {
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
                    .setTitle(TITLE)
                    .setBody("Ваш заказ №$orderCode готов к выдаче")
                    .build()
            )
            .setToken(notificationToken)
            .setFcmOptions(
                FcmOptions.withAnalyticsLabel(ANALYTICS_LABEL)
            )
            .build()
    }
}
