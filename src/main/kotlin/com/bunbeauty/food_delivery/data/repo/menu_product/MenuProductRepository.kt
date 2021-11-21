package com.bunbeauty.food_delivery.data.repo.menu_product

import com.bunbeauty.food_delivery.data.DatabaseFactory.query
import com.bunbeauty.food_delivery.data.entity.MenuProductEntity

class MenuProductRepository : IMenuProductRepository {

    override suspend fun getMenuProductList(): List<MenuProductEntity> = query {
        MenuProductEntity.all().toList()
    }
}