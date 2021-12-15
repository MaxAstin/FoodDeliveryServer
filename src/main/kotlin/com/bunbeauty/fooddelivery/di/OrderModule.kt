package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.data.repo.order.IOrderRepository
import com.bunbeauty.fooddelivery.data.repo.order.OrderRepository
import com.bunbeauty.fooddelivery.service.order.IOrderService
import com.bunbeauty.fooddelivery.service.order.OrderService
import org.koin.dsl.module

val orderModule = module(createdAtStart = true) {
    single<IOrderService> { OrderService(get(), get()) }
    single<IOrderRepository> { OrderRepository() }
}