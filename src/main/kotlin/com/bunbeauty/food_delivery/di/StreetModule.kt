package com.bunbeauty.food_delivery.di

import com.bunbeauty.food_delivery.data.repo.street.IStreetRepository
import com.bunbeauty.food_delivery.data.repo.street.StreetRepository
import com.bunbeauty.food_delivery.service.street.IStreetService
import com.bunbeauty.food_delivery.service.street.StreetService
import org.koin.dsl.module

val streetModule = module(createdAtStart = true) {
    single<IStreetService> { StreetService(get()) }
    single<IStreetRepository> { StreetRepository() }
}