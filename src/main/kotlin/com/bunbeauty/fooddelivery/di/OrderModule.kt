package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.data.features.order.OrderRepository
import com.bunbeauty.fooddelivery.domain.feature.order.OrderService
import com.bunbeauty.fooddelivery.domain.feature.order.PolygonHelper
import org.koin.dsl.module

val orderModule = module(createdAtStart = true) {
    factory { PolygonHelper() }
    factory {
        OrderService(
            orderRepository = get(),
            addressRepository = get(),
            clientUserRepository = get(),
            menuProductRepository = get(),
            cafeRepository = get(),
            firebaseMessaging = get(),
            polygonHelper = get(),
        )
    }
    single { OrderRepository() }
}