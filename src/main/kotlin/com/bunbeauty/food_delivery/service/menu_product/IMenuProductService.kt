package com.bunbeauty.food_delivery.service.menu_product

import com.bunbeauty.food_delivery.data.model.menu_product.GetMenuProduct
import com.bunbeauty.food_delivery.data.model.menu_product.PostMenuProduct

interface IMenuProductService {

    suspend fun createMenuProduct(postMenuProduct: PostMenuProduct, creatorUuid: String): GetMenuProduct?
    suspend fun getMenuProductListByCompanyUuid(companyUuid: String): List<GetMenuProduct>
}