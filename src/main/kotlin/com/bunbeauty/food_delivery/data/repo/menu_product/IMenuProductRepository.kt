package com.bunbeauty.food_delivery.data.repo.menu_product

import com.bunbeauty.food_delivery.data.entity.MenuProductEntity

interface IMenuProductRepository {

    suspend fun getMenuProductList(): List<MenuProductEntity>
}