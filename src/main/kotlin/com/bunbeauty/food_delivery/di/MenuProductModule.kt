package com.bunbeauty.food_delivery.di

import com.bunbeauty.food_delivery.data.repo.menu_product.IMenuProductRepository
import com.bunbeauty.food_delivery.data.repo.menu_product.MenuProductRepository
import com.bunbeauty.food_delivery.service.menu_product.IMenuProductService
import com.bunbeauty.food_delivery.service.menu_product.MenuProductService
import org.koin.dsl.module

val menuProductModule = module(createdAtStart = true) {
    single<IMenuProductService> { MenuProductService(get(), get()) }
    single<IMenuProductRepository> { MenuProductRepository() }
}