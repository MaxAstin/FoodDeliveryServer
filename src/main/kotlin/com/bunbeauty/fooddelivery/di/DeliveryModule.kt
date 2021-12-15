package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.service.delivery.DeliveryService
import com.bunbeauty.fooddelivery.service.delivery.IDeliveryService
import org.koin.dsl.module

val deliveryModule = module(createdAtStart = true) {
    single<IDeliveryService> { DeliveryService(get()) }
}