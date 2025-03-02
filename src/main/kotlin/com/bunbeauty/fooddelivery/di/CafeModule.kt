package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.data.features.cafe.CafeRepository
import com.bunbeauty.fooddelivery.domain.feature.cafe.CafeService
import com.bunbeauty.fooddelivery.domain.feature.cafe.GetWorkInfoByCafeUseCase
import org.koin.dsl.module

val cafeModule = module(createdAtStart = true) {
    factory {
        CafeService(
            cafeRepository = get(),
            privacyCheckService = get(),
            getWorkInfoByCafeUseCase = get()
        )
    }
    factory {
        GetWorkInfoByCafeUseCase(cafeRepository = get())
    }
    single { CafeRepository() }
}
