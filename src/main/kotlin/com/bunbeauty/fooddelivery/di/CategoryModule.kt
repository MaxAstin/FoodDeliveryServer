package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.data.features.menu.CategoryRepository
import com.bunbeauty.fooddelivery.domain.feature.menu.service.CategoryService
import org.koin.dsl.module

val categoryModule = module(createdAtStart = true) {
    single { CategoryService(get(), get()) }
    single { CategoryRepository() }
}