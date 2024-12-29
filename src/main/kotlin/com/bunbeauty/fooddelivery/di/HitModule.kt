package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.data.features.menu.HitRepository
import com.bunbeauty.fooddelivery.domain.feature.menu.service.HitService
import org.koin.dsl.module

val hitModule = module(createdAtStart = true) {
    single {
        HitService(
            companyRepository = get(),
            menuProductRepository = get(),
            orderRepository = get(),
            hitRepository = get()
        )
    }
    single { HitRepository() }
}
