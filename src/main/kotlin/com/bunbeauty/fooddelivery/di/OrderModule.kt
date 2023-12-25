package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.data.features.order.OrderRepository
import com.bunbeauty.fooddelivery.domain.feature.order.OrderService
import org.koin.dsl.module

val orderModule = module(createdAtStart = true) {
    factory {
        OrderService(
            orderRepository = get(),
            addressRepository = get(),
            clientUserRepository = get(),
            menuProductRepository = get(),
            cafeRepository = get(),
            firebaseMessaging = get()
        )
    }
    single { OrderRepository() }
}