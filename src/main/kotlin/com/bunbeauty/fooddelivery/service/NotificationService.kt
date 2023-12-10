package com.bunbeauty.fooddelivery.service

import com.bunbeauty.fooddelivery.data.repo.UserRepository
import com.bunbeauty.fooddelivery.domain.error.orThrowNotFoundByUuidError
import com.bunbeauty.fooddelivery.domain.model.notification.PostNotification
import com.bunbeauty.fooddelivery.domain.toUuid
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification

private const val NEWS_NOTIFICATION_PREFIX = "NEWS_"

class NotificationService(
    private val userRepository: UserRepository,
    private val firebaseMessaging: FirebaseMessaging,
) {

    suspend fun sendNotification(userUuid: String, postNotification: PostNotification): String {
        val user = userRepository.getUserByUuid(userUuid.toUuid())
            .orThrowNotFoundByUuidError(userUuid)
        val companyUuid = user.company.uuid
        return firebaseMessaging.send(
            Message.builder()
                .setNotification(
                    Notification.builder()
                        .setTitle(postNotification.title)
                        .setBody(postNotification.body)
                        .build()
                )
                .setTopic("$NEWS_NOTIFICATION_PREFIX$companyUuid")
                .build()
        )
    }
}