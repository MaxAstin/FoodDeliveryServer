package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.domain.feature.privacy.PrivacyCheckService
import org.koin.dsl.module

val privacyCheckModule = module(createdAtStart = true) {
    factory {
        PrivacyCheckService(
            userRepository = get()
        )
    }
}
