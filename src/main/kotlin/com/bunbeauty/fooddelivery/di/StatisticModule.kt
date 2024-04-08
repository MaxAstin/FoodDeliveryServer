package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.data.features.statistic.OrderStatisticRepository
import com.bunbeauty.fooddelivery.data.repo.statistic.CafeStatisticRepository
import com.bunbeauty.fooddelivery.data.repo.statistic.CompanyStatisticRepository
import com.bunbeauty.fooddelivery.data.repo.statistic.ICafeStatisticRepository
import com.bunbeauty.fooddelivery.data.repo.statistic.ICompanyStatisticRepository
import com.bunbeauty.fooddelivery.domain.feature.statistic.StatisticService
import org.koin.dsl.module

val statisticModule = module {
    single {
        StatisticService(
            orderStatisticRepository = get(),
            calculateOrderProductsNewCostUseCase = get(),
            calculateOrderProductTotalUseCase = get(),
            calculateCostWithDiscountUseCase = get(),
            companyRepository = get(),
            cafeRepository = get(),
            companyStatisticRepository = get(),
            cafeStatisticRepository = get(),
            userRepository = get(),
        )
    }
    single {
        OrderStatisticRepository()
    }
    single<ICompanyStatisticRepository> {
        CompanyStatisticRepository()
    }
    single<ICafeStatisticRepository> {
        CafeStatisticRepository()
    }
}