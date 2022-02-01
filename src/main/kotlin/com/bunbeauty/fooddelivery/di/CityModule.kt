package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.data.repo.city.CityRepository
import com.bunbeauty.fooddelivery.data.repo.city.ICityRepository
import com.bunbeauty.fooddelivery.service.city.CityService
import com.bunbeauty.fooddelivery.service.city.ICityService
import org.koin.dsl.module

val cityModule = module(createdAtStart = true) {
    single<ICityService> {
        CityService(
            cityRepository = get()
        )
    }
    single<ICityRepository> { CityRepository() }
}