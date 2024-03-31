package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.data.features.order.OrderRepository
import com.bunbeauty.fooddelivery.domain.feature.order.OrderService
import com.bunbeauty.fooddelivery.domain.feature.order.usecase.*
import org.koin.dsl.module

val orderModule = module(createdAtStart = true) {
    factory { CheckIsPointInPolygonUseCase() }
    factory {
        CalculateOrderProductsNewCostUseCase(
            calculateOrderProductTotalUseCase = get()
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
            calculateOrderProductTotalUseCase = get(),
        )
    }
    factory {
        GetDeliveryCostUseCase(
            clientUserRepository = get(),
            calculateOrderProductsNewCostUseCase = get(),
        )
    }
    factory {
        OrderService(
            orderRepository = get(),
            addressRepository = get(),
            clientUserRepository = get(),
            menuProductRepository = get(),
            cafeRepository = get(),
            firebaseMessaging = get(),
            checkIsPointInPolygonUseCase = get(),
            calculateOrderTotalUseCase = get(),
            getDeliveryCostUseCase = get(),
        )
    }
    single { OrderRepository() }
}