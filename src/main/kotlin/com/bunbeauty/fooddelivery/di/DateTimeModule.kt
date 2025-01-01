package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.service.date_time.DateTimeService
import com.bunbeauty.fooddelivery.service.date_time.IDateTimeService
import org.koin.dsl.module

val dateTimeModule = module(createdAtStart = true) {
    single<IDateTimeService> { DateTimeService() }
}
