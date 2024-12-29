package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.data.features.order.OrderRepository
import com.bunbeauty.fooddelivery.domain.feature.order.OrderService
import com.bunbeauty.fooddelivery.domain.feature.order.usecase.*
import org.koin.dsl.module

val orderModule = module(createdAtStart = true) {
    factory { CheckIsPointInPolygonUseCase() }
    factory {
        CalculateOrderProductsNewCostUseCase(
            calculateOrderProductTotalUseCase = get(),
            calculateCostWithDiscountUseCase = get()
        )
    }
    factory {
        CalculateOrderProductsOldCostUseCase(
            calculateOrderProductTotalUseCase = get()
        )
    }
    factory { CalculateOrderProductTotalUseCase() }
    factory {
        CalculateOrderTotalUseCase(
            calculateOrderProductsNewCostUseCase = get(),
            calculateOrderProductsOldCostUseCase = get(),
            calculateOrderProductTotalUseCase = get()
        )
    }
    factory {
        GetDeliveryCostUseCase(
            clientUserRepository = get(),
            calculateOrderProductsNewCostUseCase = get()
        )
    }
    factory {
        FindDeliveryZoneByCityUuidAndCoordinatesUseCase(
            cafeRepository = get(),
            checkIsPointInPolygonUseCase = get()
        )
    }
    factory {
        CalculateCostWithDiscountUseCase()
    }
    factory {
        IsOrderAvailableUseCase(
            companyRepository = get()
        )
    }
    factory {
        OrderService(
            orderRepository = get(),
            addressRepository = get(),
            clientUserRepository = get(),
            menuProductRepository = get(),
            cafeRepository = get(),
            notificationService = get(),
            findDeliveryZoneByCityUuidAndCoordinatesUseCase = get(),
            calculateOrderTotalUseCase = get(),
            getDeliveryCostUseCase = get(),
            isOrderAvailableUseCase = get()
        )
    }
    single { OrderRepository() }
}
