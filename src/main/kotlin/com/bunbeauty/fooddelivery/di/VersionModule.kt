package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.domain.feature.version.VersionService
import org.koin.dsl.module

val versionModule = module(createdAtStart = true) {
    single { VersionService(companyRepository = get()) }
}