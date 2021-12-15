package com.bunbeauty.food_delivery.di

import com.bunbeauty.food_delivery.service.date_time.DateTimeService
import com.bunbeauty.food_delivery.service.date_time.IDateTimeService
import com.bunbeauty.food_delivery.service.statistic.IStatisticService
import com.bunbeauty.food_delivery.service.statistic.StatisticService
import org.koin.dsl.module

val dateTimeModule = module(createdAtStart = true) {
    single<IDateTimeService> { DateTimeService() }
}