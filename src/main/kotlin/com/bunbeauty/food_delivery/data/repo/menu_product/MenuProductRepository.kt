package com.bunbeauty.food_delivery.data.repo.menu_product

import com.bunbeauty.food_delivery.data.DatabaseFactory.query
import com.bunbeauty.food_delivery.data.entity.MenuProductEntity
import com.bunbeauty.food_delivery.data.model.menu_product.GetMenuProduct

class MenuProductRepository : IMenuProductRepository {

    override suspend fun getMenuProductList(): List<GetMenuProduct> = query {
        MenuProductEntity.all()
            .map { menuProductEntity ->
                menuProductEntity.toMenuProduct()
            }.toList()
    }
}