package com.bunbeauty.food_delivery.di

import com.bunbeauty.food_delivery.service.statistic.IStatisticService
import com.bunbeauty.food_delivery.service.statistic.StatisticService
import org.koin.dsl.module

val statisticModule = module(createdAtStart = true) {
    single<IStatisticService> { StatisticService(get(), get(), get()) }
}