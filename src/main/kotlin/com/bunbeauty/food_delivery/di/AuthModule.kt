package com.bunbeauty.food_delivery.di

import com.bunbeauty.food_delivery.auth.IJwtService
import com.bunbeauty.food_delivery.auth.JwtService
import org.koin.dsl.module

val authModule = module(createdAtStart = true) {
    single<IJwtService> { JwtService() }
}