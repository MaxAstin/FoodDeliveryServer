package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.data.features.order.OrderRepository
import com.bunbeauty.fooddelivery.domain.feature.order.OrderService
import com.bunbeauty.fooddelivery.domain.feature.order.usecase.CalculateCostWithDiscountUseCase
import com.bunbeauty.fooddelivery.domain.feature.order.usecase.CalculateOrderProductTotalUseCase
import com.bunbeauty.fooddelivery.domain.feature.order.usecase.CalculateOrderProductsNewCostUseCase
import com.bunbeauty.fooddelivery.domain.feature.order.usecase.CalculateOrderProductsOldCostUseCase
import com.bunbeauty.fooddelivery.domain.feature.order.usecase.CalculateOrderTotalUseCase
import com.bunbeauty.fooddelivery.domain.feature.order.usecase.CheckIsPointInPolygonUseCase
import com.bunbeauty.fooddelivery.domain.feature.order.usecase.FindDeliveryZoneByCityUuidAndCoordinatesUseCase
import com.bunbeauty.fooddelivery.domain.feature.order.usecase.GetDeliveryCostUseCase
import com.bunbeauty.fooddelivery.domain.feature.order.usecase.IsOrderAvailableUseCase
import com.bunbeauty.fooddelivery.domain.feature.order.usecase.IsOrderAvailableV2UseCase
import com.bunbeauty.fooddelivery.domain.feature.order.usecase.UpdateOrderStatusUseCase
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
        IsOrderAvailableV2UseCase(
            companyRepository = get(),
            cafeRepository = get()
        )
    }
    factory {
        UpdateOrderStatusUseCase(
            orderRepository = get(),
            sendPickupClientNotificationUseCase = get()
        )
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
            menuProductRepository = get(),
            cafeRepository = get(),
            notificationService = get(),
            findDeliveryZoneByCityUuidAndCoordinatesUseCase = get(),
            calculateOrderTotalUseCase = get(),
            getDeliveryCostUseCase = get(),
            isOrderAvailableV2UseCase = get(),
            companyRepository = get(),
            isOrderAvailableUseCase = get(),
            updateOrderStatusUseCase = get()
        )
    }
    single { OrderRepository() }
}
