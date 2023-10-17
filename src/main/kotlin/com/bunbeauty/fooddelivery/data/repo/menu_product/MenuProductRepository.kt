package com.bunbeauty.fooddelivery.data.repo.menu_product

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.CategoryEntity
import com.bunbeauty.fooddelivery.data.entity.MenuProductEntity
import com.bunbeauty.fooddelivery.data.entity.company.CompanyEntity
import com.bunbeauty.fooddelivery.data.model.menu_product.GetMenuProduct
import com.bunbeauty.fooddelivery.data.model.menu_product.InsertMenuProduct
import com.bunbeauty.fooddelivery.data.model.menu_product.UpdateMenuProduct
import com.bunbeauty.fooddelivery.data.table.MenuProductTable
import org.jetbrains.exposed.sql.SizedCollection
import java.util.*

class MenuProductRepository {

    suspend fun insertMenuProduct(insertMenuProduct: InsertMenuProduct): GetMenuProduct = query {
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
            company = CompanyEntity[insertMenuProduct.companyUuid]
            categories = SizedCollection(insertMenuProduct.categoryUuids.map { categoryUuid ->
                CategoryEntity[categoryUuid]
            })
        }.toMenuProduct()
    }

    suspend fun updateMenuProduct(
        menuProductUuid: UUID,
        updateMenuProduct: UpdateMenuProduct,
    ): GetMenuProduct? = query {
        MenuProductEntity.findById(menuProductUuid)?.apply {
            name = updateMenuProduct.name ?: name
            newPrice = updateMenuProduct.newPrice ?: newPrice
            oldPrice = (updateMenuProduct.oldPrice ?: oldPrice)?.takeIf { oldPrice ->
                oldPrice != 0
            }
            utils = (updateMenuProduct.utils ?: utils)?.takeIf { utils ->
                utils.isNotEmpty() && (updateMenuProduct.nutrition ?: nutrition) != 0
            }
            nutrition = (updateMenuProduct.nutrition ?: nutrition)?.takeIf { nutrition ->
                ((updateMenuProduct.utils ?: utils)?.isNotEmpty() == true) && nutrition != 0
            }
            description = updateMenuProduct.description ?: description
            comboDescription = (updateMenuProduct.comboDescription ?: comboDescription)?.takeIf { comboDescription ->
                comboDescription.isNotEmpty()
            }
            photoLink = updateMenuProduct.photoLink ?: photoLink
            barcode = updateMenuProduct.barcode ?: barcode
            isVisible = updateMenuProduct.isVisible ?: isVisible
            updateMenuProduct.categoryUuids?.let { categoryUuids ->
                categories = SizedCollection(categoryUuids.map { categoryUuid ->
                    CategoryEntity[categoryUuid]
                })
            }
        }?.toMenuProduct()
    }

    suspend fun getMenuProductListByCompanyUuid(companyUuid: UUID): List<GetMenuProduct> = query {
        MenuProductEntity.find {
            MenuProductTable.company eq companyUuid
        }.map { menuProductEntity ->
            menuProductEntity.toMenuProduct()
        }.toList()
    }

    suspend fun getMenuProductByUuid(uuid: UUID): GetMenuProduct? = query {
        MenuProductEntity.findById(uuid)?.toMenuProduct()
    }
}