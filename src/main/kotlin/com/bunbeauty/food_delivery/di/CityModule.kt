package com.bunbeauty.food_delivery.di

import com.bunbeauty.food_delivery.data.repo.city.CityRepository
import com.bunbeauty.food_delivery.data.repo.city.ICityRepository
import com.bunbeauty.food_delivery.service.city.CityService
import com.bunbeauty.food_delivery.service.city.ICityService
import org.koin.dsl.module

val cityModule = module(createdAtStart = true) {
    single<ICityService> { CityService(get(), get()) }
    single<ICityRepository> { CityRepository() }
}