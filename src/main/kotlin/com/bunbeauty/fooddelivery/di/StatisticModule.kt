package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.data.features.statistic.CompanyStatisticRepository
import com.bunbeauty.fooddelivery.data.features.statistic.OrderStatisticRepository
import com.bunbeauty.fooddelivery.data.repo.statistic.CafeStatisticRepository
import com.bunbeauty.fooddelivery.data.repo.statistic.ICafeStatisticRepository
import com.bunbeauty.fooddelivery.domain.feature.statistic.GetLastMonthCompanyStatisticUseCase
import com.bunbeauty.fooddelivery.domain.feature.statistic.StatisticService
import org.koin.dsl.module

val statisticModule = module {
    factory {
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
            getLastMonthCompanyStatisticUseCase = get(),
        )
    }
    factory {
        GetLastMonthCompanyStatisticUseCase(
            companyRepository = get(),
            companyStatisticRepository = get(),
        )
    }
    single {
        OrderStatisticRepository()
    }
    single {
        CompanyStatisticRepository()
    }
    single<ICafeStatisticRepository> {
        CafeStatisticRepository()
    }
}