package com.bunbeauty.fooddelivery.data.features.menu

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.company.CompanyEntity
import com.bunbeauty.fooddelivery.data.entity.menu.CategoryEntity
import com.bunbeauty.fooddelivery.data.entity.menu.MenuProductEntity
import com.bunbeauty.fooddelivery.data.entity.menu.MenuProductWithAdditionGroupEntity
import com.bunbeauty.fooddelivery.data.entity.menu.MenuProductWithAdditionGroupWithAdditionEntity
import com.bunbeauty.fooddelivery.data.features.menu.mapper.mapMenuProductEntity
import com.bunbeauty.fooddelivery.data.features.menu.mapper.mapToMenuProduct
import com.bunbeauty.fooddelivery.data.table.menu.MenuProductTable
import com.bunbeauty.fooddelivery.data.table.menu.MenuProductToAdditionGroupTable
import com.bunbeauty.fooddelivery.data.table.menu.MenuProductToAdditionGroupToAdditionTable
import com.bunbeauty.fooddelivery.domain.feature.menu.model.menuproduct.InsertMenuProduct
import com.bunbeauty.fooddelivery.domain.feature.menu.model.menuproduct.MenuProduct
import com.bunbeauty.fooddelivery.domain.feature.menu.model.menuproduct.UpdateMenuProduct
import com.bunbeauty.fooddelivery.domain.toUuid
import org.jetbrains.exposed.sql.SizedCollection
import java.util.*

class MenuProductRepository {

    suspend fun insertMenuProduct(insertMenuProduct: InsertMenuProduct): MenuProduct = query {
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
            isRecommended = insertMenuProduct.isRecommended
            isVisible = insertMenuProduct.isVisible
            company = CompanyEntity[insertMenuProduct.companyUuid]
            categories = SizedCollection(insertMenuProduct.categoryUuids.map { categoryUuid ->
                CategoryEntity[categoryUuid]
            })
        }.mapMenuProductEntity()
    }

    suspend fun updateMenuProduct(
        menuProductUuid: UUID,
        updateMenuProduct: UpdateMenuProduct,
    ): MenuProduct? = query {
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
            isRecommended = updateMenuProduct.isRecommended ?: isRecommended
            isVisible = updateMenuProduct.isVisible ?: isVisible
            updateMenuProduct.categoryUuids?.let { categoryUuids ->
                categories = SizedCollection(categoryUuids.map { categoryUuid ->
                    CategoryEntity[categoryUuid]
                })
            }
        }?.mapMenuProductEntity()
    }

    suspend fun getMenuProductListByCompanyUuid(companyUuid: String): List<MenuProduct> = query {
        MenuProductEntity.find {
            MenuProductTable.company eq companyUuid.toUuid()
        }.map(mapMenuProductEntity)
            .toList()
    }

    suspend fun getMenuProductWithAdditionListByCompanyUuid(companyUuid: String): List<MenuProduct> = query {
        MenuProductEntity.find {
            MenuProductTable.company eq companyUuid.toUuid()
        }.map(::getMenuProductWithAdditions)
    }

    suspend fun getMenuProductByUuid(uuid: UUID): MenuProduct? = query {
        MenuProductEntity.findById(uuid)?.mapMenuProductEntity()
    }

    suspend fun getMenuProductWithAdditionListByUuid(uuid: UUID): MenuProduct? = query {
        MenuProductEntity.findById(uuid)?.let(::getMenuProductWithAdditions)
    }

    private fun getMenuProductWithAdditions(menuProductEntity: MenuProductEntity): MenuProduct {
        val menuProductWithAdditionEntities = MenuProductWithAdditionGroupWithAdditionEntity.find {
            val menuProductWithAdditionGroupEntityList = MenuProductWithAdditionGroupEntity.find {
                MenuProductToAdditionGroupTable.menuProduct eq menuProductEntity.uuid.toUuid()
            }.map {
                it.id
            }

            MenuProductToAdditionGroupToAdditionTable.menuProductToAdditionGroup.inList(
                menuProductWithAdditionGroupEntityList
            )
        }

        return if (menuProductWithAdditionEntities.empty()) {
            menuProductEntity.mapMenuProductEntity()
        } else {
            menuProductWithAdditionEntities.toList().mapToMenuProduct()
        }
    }
}