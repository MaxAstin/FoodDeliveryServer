package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.service.DiscountService
import org.koin.dsl.module

val discountModule = module(createdAtStart = true) {
    factory { DiscountService(companyRepository = get()) }
}