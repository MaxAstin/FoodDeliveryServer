package com.bunbeauty.fooddelivery.service.menu_product

import com.bunbeauty.fooddelivery.data.model.menu_product.GetMenuProduct
import com.bunbeauty.fooddelivery.data.model.menu_product.PostMenuProduct

interface IMenuProductService {

    suspend fun createMenuProduct(postMenuProduct: PostMenuProduct, creatorUuid: String): GetMenuProduct?
    suspend fun getMenuProductListByCompanyUuid(companyUuid: String): List<GetMenuProduct>
}