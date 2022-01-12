package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.service.init.IInitService
import com.bunbeauty.fooddelivery.service.init.InitService
import org.koin.dsl.module

val initModule = module(createdAtStart = true) {
    single<IInitService> {
        InitService(
            companyRepository = get(),
            categoryRepository = get(),
            menuProductRepository = get(),
            cityRepository = get(),
            userRepository = get(),
            cafeRepository = get(),
            streetRepository = get()
        )
    }
}