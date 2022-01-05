package com.bunbeauty.fooddelivery.data.repo.menu_product

import com.bunbeauty.fooddelivery.data.model.menu_product.GetMenuProduct
import com.bunbeauty.fooddelivery.data.model.menu_product.InsertMenuProduct
import com.bunbeauty.fooddelivery.data.model.menu_product.UpdateMenuProduct
import java.util.*

interface IMenuProductRepository {

    suspend fun insertMenuProduct(insertMenuProduct: InsertMenuProduct): GetMenuProduct
    suspend fun updateMenuProduct(menuProductUuid: UUID, updateMenuProduct: UpdateMenuProduct): GetMenuProduct?
    suspend fun getMenuProductListByCompanyUuid(companyUuid: UUID): List<GetMenuProduct>
}