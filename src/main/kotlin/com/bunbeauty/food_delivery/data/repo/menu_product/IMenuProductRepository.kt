package com.bunbeauty.food_delivery.data.repo.menu_product

import com.bunbeauty.food_delivery.data.entity.MenuProductEntity
import com.bunbeauty.food_delivery.data.model.menu_product.GetMenuProduct

interface IMenuProductRepository {

    suspend fun getMenuProductList(): List<GetMenuProduct>
}