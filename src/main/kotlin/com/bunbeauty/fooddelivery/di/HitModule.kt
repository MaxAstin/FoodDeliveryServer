package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.data.repo.hit.HitRepository
import com.bunbeauty.fooddelivery.data.repo.hit.IHitRepository
import com.bunbeauty.fooddelivery.service.hit.HitService
import com.bunbeauty.fooddelivery.service.hit.IHitService
import org.koin.dsl.module

val hitModule = module(createdAtStart = true) {
    single<IHitService> {
        HitService(
            companyRepository = get(),
            menuProductRepository = get(),
            orderRepository = get(),
            hitRepository = get(),
        )
    }
    single<IHitRepository> { HitRepository() }
}