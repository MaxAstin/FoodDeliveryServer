package com.bunbeauty.fooddelivery.di

import kotlinx.serialization.json.Json
import org.koin.dsl.module

val dataModule = module(createdAtStart = true) {
    single {
        Json {
            isLenient = false
            ignoreUnknownKeys = true
        }
    }
}