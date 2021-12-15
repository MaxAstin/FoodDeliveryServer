package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.data.repo.street.IStreetRepository
import com.bunbeauty.fooddelivery.data.repo.street.StreetRepository
import com.bunbeauty.fooddelivery.service.street.IStreetService
import com.bunbeauty.fooddelivery.service.street.StreetService
import org.koin.dsl.module

val streetModule = module(createdAtStart = true) {
    single<IStreetService> { StreetService(get()) }
    single<IStreetRepository> { StreetRepository() }
}