package com.bunbeauty.food_delivery.service.menu_product

import com.bunbeauty.food_delivery.data.model.menu_product.GetMenuProduct
import com.bunbeauty.food_delivery.data.repo.menu_product.IMenuProductRepository

class MenuProductService(private val menuProductRepository: IMenuProductRepository) : IMenuProductService {

    override suspend fun getMenuProductList(): List<GetMenuProduct> =
        menuProductRepository.getMenuProductList().map { menuProductEntity ->
            menuProductEntity.toMenuProduct()
        }

}