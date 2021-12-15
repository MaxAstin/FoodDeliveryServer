package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.auth.IJwtService
import com.bunbeauty.fooddelivery.auth.JwtService
import com.google.firebase.auth.FirebaseAuth
import org.koin.dsl.module

val authModule = module(createdAtStart = true) {
    single<FirebaseAuth> { FirebaseAuth.getInstance() }
    single<IJwtService> { JwtService() }
}