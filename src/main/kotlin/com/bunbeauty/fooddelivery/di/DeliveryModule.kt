package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.domain.feature.delivery.DeliveryService
import org.koin.dsl.module

val deliveryModule = module(createdAtStart = true) {
    factory { DeliveryService(get()) }
}