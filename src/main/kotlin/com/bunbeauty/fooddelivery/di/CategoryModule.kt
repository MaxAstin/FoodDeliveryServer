package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.data.features.menu.CategoryRepository
import com.bunbeauty.fooddelivery.domain.feature.menu.service.CategoryService
import com.bunbeauty.fooddelivery.domain.feature.menu.usecase.UpdateCategoryUseCase
import org.koin.dsl.module

val categoryModule = module(createdAtStart = true) {
    single {
        CategoryService(
            categoryRepository = get(),
            userRepository = get(),
            updateCategoryUseCase = get()
        )
    }
    single { CategoryRepository() }
    factory { UpdateCategoryUseCase(categoryRepository = get()) }
}
