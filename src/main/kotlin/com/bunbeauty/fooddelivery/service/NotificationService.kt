package com.bunbeauty.fooddelivery.service

import com.bunbeauty.fooddelivery.data.features.cafe.CafeRepository
import com.bunbeauty.fooddelivery.data.features.user.UserRepository
import com.bunbeauty.fooddelivery.domain.error.orThrowNotFoundByUuidError
import com.bunbeauty.fooddelivery.domain.model.notification.PostNotification
import com.bunbeauty.fooddelivery.domain.toUuid
import com.google.firebase.messaging.*

private const val NEWS_NOTIFICATION_PREFIX = "NEWS_"
private const val ORDER_CODE_KEY = "orderCode"

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
            val userTokenList = userRepository.getUserListByCityUuid(
                cityUuid = cafe.cityUuid.toUuid()
            ).map { user ->
                user.notificationToken
            }

            firebaseMessaging.sendEachForMulticast(
                MulticastMessage.builder()
                    .setNotification(
                        createNewOrderNotification(orderCode = orderCode)
                    )
                    .addAllTokens(userTokenList)
                    .putData(ORDER_CODE_KEY, orderCode)
                    .setFcmOptions(
                        FcmOptions.withAnalyticsLabel("order")
                    )
                    .build()
            )
            firebaseMessaging.send(
                Message.builder()
                    .setNotification(
                        createNewOrderNotification(orderCode = orderCode)
                    )
                    .setTopic(cafeUuid)
                    .putData(ORDER_CODE_KEY, orderCode)
                    .setFcmOptions(
                        FcmOptions.withAnalyticsLabel("order")
                    )
                    .build()
            )

            println("sendNotification success")
        } catch (exception: Exception) {
            println("sendNotification exception:")
            exception.printStackTrace()
        }
    }

    private fun createNewOrderNotification(orderCode: String): Notification {
        return  Notification.builder()
            .setTitle(orderCode)
            .setBody("Новый заказ")
            .build()
    }

}