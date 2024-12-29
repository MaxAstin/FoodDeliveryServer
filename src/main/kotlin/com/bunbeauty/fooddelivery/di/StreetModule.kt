package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.data.features.address.StreetRepository
import com.bunbeauty.fooddelivery.domain.feature.address.StreetService
import org.koin.dsl.module

val streetModule = module(createdAtStart = true) {
    factory { StreetService(get()) }
    single { StreetRepository() }
}
