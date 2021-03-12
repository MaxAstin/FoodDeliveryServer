package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.service.version.IVersionService
import com.bunbeauty.fooddelivery.service.version.VersionService
import org.koin.dsl.module

val versionModule = module(createdAtStart = true) {
    single<IVersionService> { VersionService(get()) }
}