package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.data.repo.menu_product.MenuProductRepository
import com.bunbeauty.fooddelivery.service.menu_product.IMenuProductService
import com.bunbeauty.fooddelivery.service.menu_product.MenuProductService
import org.koin.dsl.module

val menuProductModule = module(createdAtStart = true) {
    factory<IMenuProductService> {
        MenuProductService(
            menuProductRepository = get(),
            userRepository = get(),
            categoryRepository = get(),
            hitRepository = get()
        )
    }
    single { MenuProductRepository() }
}