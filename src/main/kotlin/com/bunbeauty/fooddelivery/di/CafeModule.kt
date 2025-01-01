package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.data.features.cafe.CafeRepository
import com.bunbeauty.fooddelivery.domain.feature.cafe.CafeService
import org.koin.dsl.module

val cafeModule = module(createdAtStart = true) {
    factory {
        CafeService(
            cafeRepository = get(),
            privacyCheckService = get()
        )
    }
    single { CafeRepository() }
}