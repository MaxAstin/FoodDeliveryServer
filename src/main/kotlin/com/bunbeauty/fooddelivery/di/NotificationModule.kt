package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.service.NotificationService
import org.koin.dsl.module

val notificationModule = module(createdAtStart = true) {
    single {
        NotificationService(
            cafeRepository = get(),
            userRepository = get(),
            firebaseMessaging = get()
        )
    }
}