package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.service.statistic.IStatisticService
import com.bunbeauty.fooddelivery.service.statistic.StatisticService
import org.koin.dsl.module

val statisticModule = module(createdAtStart = true) {
    single<IStatisticService> { StatisticService(get(), get(), get()) }
}