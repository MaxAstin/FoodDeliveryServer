package com.bunbeauty.food_delivery.di

import com.bunbeauty.food_delivery.data.mapper.order.IOrderMapper
import com.bunbeauty.food_delivery.data.mapper.order.OrderMapper
import com.bunbeauty.food_delivery.data.repo.order.IOrderRepository
import com.bunbeauty.food_delivery.data.repo.order.OrderRepository
import com.bunbeauty.food_delivery.service.order.IOrderService
import com.bunbeauty.food_delivery.service.order.OrderService
import org.koin.dsl.module

val orderModule = module(createdAtStart = true) {
    single<IOrderService> { OrderService(get(), get()) }
    single<IOrderRepository> { OrderRepository() }
    single<IOrderMapper> { OrderMapper() }
}