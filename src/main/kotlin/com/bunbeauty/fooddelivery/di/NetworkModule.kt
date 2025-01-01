package com.bunbeauty.fooddelivery.di

import kotlinx.serialization.json.Json
import org.koin.dsl.module

val networkModule = module(createdAtStart = true) {

    factory {
        Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
            encodeDefaults = true
        }
    }

}