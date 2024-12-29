package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.data.repo.NonWorkingDayRepository
import com.bunbeauty.fooddelivery.service.NonWorkingDayService
import org.koin.dsl.module

val nonWorkingDayModule = module(createdAtStart = true) {
    factory {
        NonWorkingDayService(
            nonWorkingDayRepository = get()
        )
    }
    single { NonWorkingDayRepository() }
}
