package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.data.repo.cafe.CafeRepository
import com.bunbeauty.fooddelivery.data.repo.cafe.ICafeRepository
import com.bunbeauty.fooddelivery.service.cafe.CafeService
import com.bunbeauty.fooddelivery.service.cafe.ICafeService
import org.koin.dsl.module

val cafeModule = module(createdAtStart = true) {
    single<ICafeService> { CafeService(get()) }
    single<ICafeRepository> { CafeRepository() }
}