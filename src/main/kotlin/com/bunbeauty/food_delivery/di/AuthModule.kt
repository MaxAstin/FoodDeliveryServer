package com.bunbeauty.food_delivery.di

import com.bunbeauty.food_delivery.auth.IJwtService
import com.bunbeauty.food_delivery.auth.JwtService
import com.google.firebase.auth.FirebaseAuth
import org.koin.dsl.module

val authModule = module(createdAtStart = true) {
    single<FirebaseAuth> { FirebaseAuth.getInstance() }
    single<IJwtService> { JwtService() }
}