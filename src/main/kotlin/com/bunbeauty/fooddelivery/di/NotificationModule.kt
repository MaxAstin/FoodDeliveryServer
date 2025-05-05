package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.domain.feature.notification.GetClientNotificationUseCase
import com.bunbeauty.fooddelivery.domain.feature.notification.GetOrderNotificationUseCase
import com.bunbeauty.fooddelivery.domain.feature.notification.SendPickupClientNotificationUseCase
import com.bunbeauty.fooddelivery.service.NotificationService
import org.koin.dsl.module

val notificationModule = module(createdAtStart = true) {
    single {
        NotificationService(
            userRepository = get(),
            firebaseMessaging = get(),
            getOrderNotificationUseCase = get(),
            getClientNotificationUseCase = get()
        )
    }
    factory {
        GetOrderNotificationUseCase()
    }
    factory {
        SendPickupClientNotificationUseCase(
            userClientRepository = get(),
            firebaseMessaging = get()
        )
    }
    factory {
        GetClientNotificationUseCase(
            userClientRepository = get()
        )
    }
}
