package com.bunbeauty.fooddelivery.service

import com.bunbeauty.fooddelivery.data.ext.toUuid
import com.bunbeauty.fooddelivery.data.model.notification.PostNotification
import com.bunbeauty.fooddelivery.data.repo.user.UserRepository
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification

private const val NEWS_NOTIFICATION_PREFIX = "NEWS"

class NotificationService(
    private val userRepository: UserRepository,
    private val firebaseMessaging: FirebaseMessaging,
) {

    suspend fun sendNotification(userUuid: String, postNotification: PostNotification): String? {
        val user = userRepository.getUserByUuid(userUuid.toUuid()) ?: return null
        val companyUuid = user.company.uuid
        return firebaseMessaging.send(
            Message.builder()
                .setNotification(
                    Notification.builder()
                        .setTitle(postNotification.title)
                        .setBody(postNotification.body)
                        .build()
                )
                .setTopic("$NEWS_NOTIFICATION_PREFIX $companyUuid")
                .build()
        )
    }
}