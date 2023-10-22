package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.data.repo.CafeRepository
import com.bunbeauty.fooddelivery.service.CafeService
import org.koin.dsl.module

val cafeModule = module(createdAtStart = true) {
    factory {
        CafeService(
            cafeRepository = get()
        )
    }
    single { CafeRepository() }
}