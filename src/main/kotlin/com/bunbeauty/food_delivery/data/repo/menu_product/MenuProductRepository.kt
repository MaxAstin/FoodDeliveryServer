package com.bunbeauty.food_delivery.data.repo.menu_product

import com.bunbeauty.food_delivery.data.DatabaseFactory.query
import com.bunbeauty.food_delivery.data.entity.CategoryEntity
import com.bunbeauty.food_delivery.data.entity.CompanyEntity
import com.bunbeauty.food_delivery.data.entity.MenuProductEntity
import com.bunbeauty.food_delivery.data.model.menu_product.GetMenuProduct
import com.bunbeauty.food_delivery.data.model.menu_product.InsertMenuProduct
import com.bunbeauty.food_delivery.data.table.MenuProductTable
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.booleanLiteral
import java.util.*

class MenuProductRepository : IMenuProductRepository {

    override suspend fun insertMenuProduct(insertMenuProduct: InsertMenuProduct): GetMenuProduct = query {
        MenuProductEntity.new {
            name = insertMenuProduct.name
            newPrice = insertMenuProduct.newPrice
            oldPrice = insertMenuProduct.oldPrice
            utils = insertMenuProduct.utils
            nutrition = insertMenuProduct.nutrition
            description = insertMenuProduct.description
            comboDescription = insertMenuProduct.comboDescription
            photoLink = insertMenuProduct.photoLink
            barcode = insertMenuProduct.barcode
            isVisible = insertMenuProduct.isVisible
            company = CompanyEntity[insertMenuProduct.companyUUID]
            categories = SizedCollection(insertMenuProduct.categoryUuids.map { categoryUuid ->
                CategoryEntity[categoryUuid]
            })
        }.toMenuProduct()
    }

    override suspend fun getMenuProductListByCompanyUuid(companyUuid: UUID): List<GetMenuProduct> = query {
        MenuProductEntity.find {
            MenuProductTable.company eq companyUuid and
                    MenuProductTable.isVisible eq booleanLiteral(true)
        }.map { menuProductEntity ->
            menuProductEntity.toMenuProduct()
        }.toList()
    }
}