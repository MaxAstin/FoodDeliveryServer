package com.bunbeauty.fooddelivery.domain.feature.menu.service

import com.bunbeauty.fooddelivery.data.features.menu.CategoryRepository
import com.bunbeauty.fooddelivery.data.features.menu.HitRepository
import com.bunbeauty.fooddelivery.data.features.menu.MenuProductRepository
import com.bunbeauty.fooddelivery.data.repo.UserRepository
import com.bunbeauty.fooddelivery.domain.error.orThrowNotFoundByUserUuidError
import com.bunbeauty.fooddelivery.domain.feature.menu.mapper.mapMenuProduct
import com.bunbeauty.fooddelivery.domain.feature.menu.mapper.mapPatchMenuProduct
import com.bunbeauty.fooddelivery.domain.feature.menu.mapper.mapPostMenuProduct
import com.bunbeauty.fooddelivery.domain.feature.menu.model.menuproduct.GetMenuProduct
import com.bunbeauty.fooddelivery.domain.feature.menu.model.menuproduct.PatchMenuProduct
import com.bunbeauty.fooddelivery.domain.feature.menu.model.menuproduct.PostMenuProduct
import com.bunbeauty.fooddelivery.domain.toUuid

class MenuProductService(
    private val menuProductRepository: MenuProductRepository,
    private val userRepository: UserRepository,
    private val categoryRepository: CategoryRepository,
    private val hitRepository: HitRepository,
) {

    suspend fun getMenuProductListByCompanyUuid(companyUuid: String): List<GetMenuProduct> {
        val menuProductList = menuProductRepository.getMenuProductListByCompanyUuid(companyUuid.toUuid())
        val hitProductUuidList = hitRepository.getHitProductUuidListByCompanyUuid(companyUuid)
        val updatedMenuProductList = if (hitProductUuidList.isNotEmpty()) {
            val hitsCategory = categoryRepository.getHitsCategory()

            menuProductList.map { menuProduct ->
                if (hitProductUuidList.contains(menuProduct.uuid)) {
                    menuProduct.copy(categories = menuProduct.categories + hitsCategory)
                } else {
                    menuProduct
                }
            }
        } else {
            menuProductList
        }

        return updatedMenuProductList.map(mapMenuProduct)
    }

    suspend fun createMenuProduct(postMenuProduct: PostMenuProduct, creatorUuid: String): GetMenuProduct {
        val companyUuid = userRepository.getCompanyByUserUuid(creatorUuid.toUuid())
            .orThrowNotFoundByUserUuidError(creatorUuid)
            .uuid
            .toUuid()
        val insertMenuProduct = postMenuProduct.mapPostMenuProduct(companyUuid)

        return menuProductRepository.insertMenuProduct(insertMenuProduct).mapMenuProduct()
    }

    suspend fun updateMenuProduct(
        menuProductUuid: String,
        patchMenuProduct: PatchMenuProduct,
    ): GetMenuProduct? {
        val updateMenuProduct = patchMenuProduct.mapPatchMenuProduct()
        return menuProductRepository.updateMenuProduct(
            menuProductUuid = menuProductUuid.toUuid(),
            updateMenuProduct = updateMenuProduct
        )?.mapMenuProduct()
    }
}