package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.data.features.city.CityRepository
import com.bunbeauty.fooddelivery.domain.feature.city.CityService
import org.koin.dsl.module

val cityModule = module(createdAtStart = true) {
    factory {
        CityService(
            cityRepository = get(),
            userRepository = get(),
        )
    }
    single { CityRepository() }
}