package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.data.features.menu.MenuProductRepository
import com.bunbeauty.fooddelivery.data.features.menu.cache.MenuProductCatch
import com.bunbeauty.fooddelivery.domain.feature.menu.service.MenuProductService
import org.koin.dsl.module

val menuProductModule = module(createdAtStart = true) {
    factory {
        MenuProductService(
            menuProductRepository = get(),
            userRepository = get(),
            categoryRepository = get(),
            hitRepository = get()
        )
    }
    single { MenuProductRepository(menuProductCatch = get()) }
    single { MenuProductCatch() }
}