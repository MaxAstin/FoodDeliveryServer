package com.bunbeauty.fooddelivery.service

import com.bunbeauty.fooddelivery.data.features.cafe.CafeRepository
import com.bunbeauty.fooddelivery.data.features.user.UserRepository
import com.bunbeauty.fooddelivery.domain.error.orThrowNotFoundByUuidError
import com.bunbeauty.fooddelivery.domain.feature.user.User
import com.bunbeauty.fooddelivery.domain.model.notification.PostNotification
import com.bunbeauty.fooddelivery.domain.toUuid
import com.google.firebase.messaging.*

private const val NEWS_NOTIFICATION_PREFIX = "NEWS_"
private const val ORDER_CODE_KEY = "orderCode"
private const val UNLIMITED_KEY = "unlimited"
private const val ANALYTICS_LABEL = "order"

class NotificationService(
    private val cafeRepository: CafeRepository,
    private val userRepository: UserRepository,
    private val firebaseMessaging: FirebaseMessaging,
) {

    suspend fun sendNotification(userUuid: String, postNotification: PostNotification): String {
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
        try {
            val cafe = cafeRepository.getCafeByUuid(uuid = cafeUuid.toUuid())
                .orThrowNotFoundByUuidError(uuid = cafeUuid)
            userRepository.getUserListByCityUuid(
                cityUuid = cafe.cityUuid.toUuid()
            ).forEach { user ->
                buildMessage(
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

    private fun buildMessage(user: User, orderCode: String): Message? {
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