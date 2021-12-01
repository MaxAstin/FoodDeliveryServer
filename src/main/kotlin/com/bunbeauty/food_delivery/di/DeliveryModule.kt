package com.bunbeauty.food_delivery.di

import com.bunbeauty.food_delivery.service.delivery.DeliveryService
import com.bunbeauty.food_delivery.service.delivery.IDeliveryService
import org.koin.dsl.module

val deliveryModule = module(createdAtStart = true) {
    single<IDeliveryService> { DeliveryService(get()) }
}