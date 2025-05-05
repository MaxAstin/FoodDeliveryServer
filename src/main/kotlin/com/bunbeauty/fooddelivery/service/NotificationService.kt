package com.bunbeauty.fooddelivery.service

import com.bunbeauty.fooddelivery.data.features.user.UserRepository
import com.bunbeauty.fooddelivery.domain.error.orThrowNotFoundByUuidError
import com.bunbeauty.fooddelivery.domain.feature.notification.GetClientNotificationUseCase
import com.bunbeauty.fooddelivery.domain.feature.notification.GetOrderNotificationUseCase
import com.bunbeauty.fooddelivery.domain.model.notification.PostNotification
import com.bunbeauty.fooddelivery.domain.toUuid
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

private const val NEWS_NOTIFICATION_PREFIX = "NEWS_"

class NotificationService(
    private val userRepository: UserRepository,
    private val firebaseMessaging: FirebaseMessaging,
    private val getOrderNotificationUseCase: GetOrderNotificationUseCase,
    private val getClientNotificationUseCase: GetClientNotificationUseCase
) {

    suspend fun sendTopicNotification(userUuid: String, postNotification: PostNotification): String {
        val user = userRepository.getUserByUuid(userUuid.toUuid())
            .orThrowNotFoundByUuidError(userUuid)
        return firebaseMessaging.send(
            Message.builder()
                .setNotification(
                    Notification.builder()
                        .setTitle(postNotification.title)
                        .setBody(postNotification.body)
                        .build()
                )
                .setTopic("$NEWS_NOTIFICATION_PREFIX${user.companyUuid}")
                .build()
        )
    }

    suspend fun sendNotification(cafeUuid: String, orderCode: String) {
        supervisorScope {
            launch {
                try {
                    userRepository.getUserListByCafeUuid(
                        cafeUuid = cafeUuid.toUuid()
                    ).forEach { user ->
                        getOrderNotificationUseCase(
                            user = user,
                            orderCode = orderCode
                        )?.let(firebaseMessaging::send)
                    }
                    println("sendNotification success")
                } catch (exception: Exception) {
                    println("sendNotification exception:")
                    exception.printStackTrace()
                }
            }
        }
    }

    suspend fun sendClientNotification(clientUuid: String, postNotification: PostNotification) {
        try {
            getClientNotificationUseCase(
                clientUuid = clientUuid,
                postNotification = postNotification
            )?.let(firebaseMessaging::send)
            println("sendClientNotification success clientUuid $clientUuid ")
        } catch (exception: Exception) {
            println("sendPickupNotification exception:")
            exception.printStackTrace()
        }
    }
}
