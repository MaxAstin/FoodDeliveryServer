package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.data.repo.category.CategoryRepository
import com.bunbeauty.fooddelivery.data.repo.category.ICategoryRepository
import com.bunbeauty.fooddelivery.service.category.CategoryService
import com.bunbeauty.fooddelivery.service.category.ICategoryService
import org.koin.dsl.module

val categoryModule = module(createdAtStart = true) {
    single<ICategoryService> { CategoryService(get(), get()) }
    single<ICategoryRepository> { CategoryRepository() }
}