package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.data.repo.order.IOrderStatisticRepository
import com.bunbeauty.fooddelivery.data.repo.order.OrderStatisticRepository
import com.bunbeauty.fooddelivery.data.repo.statistic.CafeStatisticRepository
import com.bunbeauty.fooddelivery.data.repo.statistic.ICompanyStatisticRepository
import com.bunbeauty.fooddelivery.data.repo.statistic.CompanyStatisticRepository
import com.bunbeauty.fooddelivery.data.repo.statistic.ICafeStatisticRepository
import com.bunbeauty.fooddelivery.service.StatisticService
import org.koin.dsl.module

val statisticModule = module {
    single {
        StatisticService(
            orderStatisticRepository = get(),
            companyRepository = get(),
            cafeRepository = get(),
            companyStatisticRepository = get(),
            cafeStatisticRepository = get(),
            userRepository = get(),
        )
    }
    single<IOrderStatisticRepository> {
        OrderStatisticRepository()
    }
    single<ICompanyStatisticRepository> {
        CompanyStatisticRepository()
    }
    single<ICafeStatisticRepository> {
        CafeStatisticRepository()
    }
}