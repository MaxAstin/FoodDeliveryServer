package com.bunbeauty.fooddelivery.data.features.menu

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.company.CompanyEntity
import com.bunbeauty.fooddelivery.data.entity.menu.CategoryEntity
import com.bunbeauty.fooddelivery.data.entity.menu.MenuProductEntity
import com.bunbeauty.fooddelivery.data.entity.menu.MenuProductWithAdditionGroupEntity
import com.bunbeauty.fooddelivery.data.entity.menu.MenuProductWithAdditionGroupWithAdditionEntity
import com.bunbeauty.fooddelivery.data.features.menu.cache.MenuProductCatch
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

    private val menuProductCatch = MenuProductCatch()

    suspend fun insertMenuProduct(insertMenuProduct: InsertMenuProduct): MenuProduct = query {
        menuProductCatch.clearCache(key = insertMenuProduct.companyUuid)

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
        companyUuid: String,
        menuProductUuid: String,
        updateMenuProduct: UpdateMenuProduct,
    ): MenuProduct? = query {
        MenuProductEntity.findById(id = menuProductUuid.toUuid())?.apply {
            menuProductCatch.clearCache(key = companyUuid.toUuid())

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

    suspend fun getMenuProductListByCompanyUuid(companyUuid: String): List<MenuProduct> {
        return getMenuProductList(
            companyUuid = companyUuid.toUuid(),
            transform = mapMenuProductEntity
        )
    }

    suspend fun getMenuProductWithAdditionListByCompanyUuid(companyUuid: String): List<MenuProduct> {
        val menuProductList = getMenuProductList(
            companyUuid = companyUuid.toUuid(),
            transform = ::getMenuProductWithAdditions
        )
        menuProductCatch.setCache(
            key = companyUuid.toUuid(),
            value = menuProductList
        )

        return menuProductList
    }

    suspend fun getMenuProductByUuid(
        companyUuid: String,
        uuid: String,
    ): MenuProduct? {
        return getMenuProductByUuid(
            companyUuid = companyUuid,
            uuid = uuid,
            transform = mapMenuProductEntity
        )
    }

    suspend fun getMenuProductWithAdditionListByUuid(
        companyUuid: String,
        uuid: String,
    ): MenuProduct? {
        return getMenuProductByUuid(
            companyUuid = companyUuid,
            uuid = uuid,
            transform = ::getMenuProductWithAdditions
        )
    }

    private suspend fun getMenuProductList(
        companyUuid: UUID,
        transform: (MenuProductEntity) -> MenuProduct,
    ): List<MenuProduct> {
        val cache = menuProductCatch.getCache(key = companyUuid)
        return cache ?: query {
            MenuProductEntity.find {
                MenuProductTable.company eq companyUuid
            }.map(transform)
        }
    }

    private suspend fun getMenuProductByUuid(
        companyUuid: String,
        uuid: String,
        transform: (MenuProductEntity) -> MenuProduct,
    ): MenuProduct? {
        val menuProduct = menuProductCatch.getCache(
            key = companyUuid.toUuid()
        )?.find { menuProduct ->
            menuProduct.uuid == uuid
        }
        return menuProduct ?: query {
            MenuProductEntity.findById(
                id = uuid.toUuid()
            )?.let(transform)
        }
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