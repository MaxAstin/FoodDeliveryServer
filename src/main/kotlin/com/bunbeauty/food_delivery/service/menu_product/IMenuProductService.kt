package com.bunbeauty.food_delivery.service.menu_product

import com.bunbeauty.food_delivery.data.model.menu_product.GetMenuProduct

interface IMenuProductService {

    suspend fun getMenuProductList(): List<GetMenuProduct>
}