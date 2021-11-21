package com.bunbeauty.food_delivery.di

import com.bunbeauty.food_delivery.data.repo.cafe.CafeRepository
import com.bunbeauty.food_delivery.data.repo.cafe.ICafeRepository
import com.bunbeauty.food_delivery.service.cafe.CafeService
import com.bunbeauty.food_delivery.service.cafe.ICafeService
import org.koin.dsl.module

val cafeModule = module(createdAtStart = true) {
    single<ICafeService> { CafeService(get()) }
    single<ICafeRepository> { CafeRepository() }
}