package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.service.statistic.StatisticService
import org.koin.dsl.module

val statisticModule = module(createdAtStart = true) {
    single {
        StatisticService(
            orderRepository = get(),
            dateTimeService = get(),
            userRepository = get(),
            cafeRepository = get()
        )
    }
}