package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.data.repo.order.IOrderStatisticRepository
import com.bunbeauty.fooddelivery.data.repo.order.OrderStatisticRepository
import com.bunbeauty.fooddelivery.data.repo.statistic.IStatisticRepository
import com.bunbeauty.fooddelivery.data.repo.statistic.StatisticRepository
import com.bunbeauty.fooddelivery.service.new_statistic.NewStatisticService
import org.koin.dsl.module

val newStatisticModule = module {
    single {
        NewStatisticService(
            orderStatisticRepository = get(),
            companyRepository = get(),
            statisticRepository = get(),
        )
    }
    single<IOrderStatisticRepository> {
        OrderStatisticRepository()
    }
    single<IStatisticRepository> {
        StatisticRepository()
    }
}