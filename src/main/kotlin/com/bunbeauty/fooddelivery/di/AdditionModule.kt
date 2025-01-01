package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.data.features.menu.AdditionRepository
import com.bunbeauty.fooddelivery.domain.feature.menu.service.AdditionService
import org.koin.dsl.module

val additionModule = module(createdAtStart = true) {
    single {
        AdditionService(
            userRepository = get(),
            additionRepository = get(),
            menuProductRepository = get()
        )
    }
    single {
        AdditionRepository(menuProductCatch = get())
    }
}
