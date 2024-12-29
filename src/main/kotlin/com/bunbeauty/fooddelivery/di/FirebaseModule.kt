package com.bunbeauty.fooddelivery.di

import com.google.firebase.messaging.FirebaseMessaging
import org.koin.dsl.module

val firebaseModule = module(createdAtStart = true) {
    single<FirebaseMessaging> { FirebaseMessaging.getInstance() }
}
