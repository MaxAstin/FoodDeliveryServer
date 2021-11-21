package com.bunbeauty.food_delivery.di

import com.bunbeauty.food_delivery.data.repo.category.CategoryRepository
import com.bunbeauty.food_delivery.data.repo.category.ICategoryRepository
import com.bunbeauty.food_delivery.service.category.CategoryService
import com.bunbeauty.food_delivery.service.category.ICategoryService
import org.koin.dsl.module

val categoryModule = module(createdAtStart = true) {
    single<ICategoryService> { CategoryService(get()) }
    single<ICategoryRepository> { CategoryRepository() }
}