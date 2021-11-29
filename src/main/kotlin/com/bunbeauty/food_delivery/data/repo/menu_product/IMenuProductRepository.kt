package com.bunbeauty.food_delivery.data.repo.menu_product

import com.bunbeauty.food_delivery.data.model.menu_product.GetMenuProduct
import com.bunbeauty.food_delivery.data.model.menu_product.InsertMenuProduct
import java.util.*

interface IMenuProductRepository {

    suspend fun insertMenuProduct(insertMenuProduct: InsertMenuProduct): GetMenuProduct
    suspend fun getMenuProductListByCompanyUuid(companyUuid: UUID): List<GetMenuProduct>
}