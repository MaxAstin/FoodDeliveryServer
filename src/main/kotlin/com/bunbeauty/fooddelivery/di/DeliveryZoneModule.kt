package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.data.features.cafe.DeliveryZoneRepository
import com.bunbeauty.fooddelivery.domain.feature.cafe.DeliveryZoneService
import org.koin.dsl.module

val deliveryZoneModule = module(createdAtStart = true) {
    factory {
        DeliveryZoneService(
            deliveryZoneRepository = get()
        )
    }
    single { DeliveryZoneRepository() }
}