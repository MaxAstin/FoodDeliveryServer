package com.bunbeauty.food_delivery.service.menu_product

import com.bunbeauty.food_delivery.data.ext.toUuid
import com.bunbeauty.food_delivery.data.model.menu_product.GetMenuProduct
import com.bunbeauty.food_delivery.data.model.menu_product.InsertMenuProduct
import com.bunbeauty.food_delivery.data.model.menu_product.PostMenuProduct
import com.bunbeauty.food_delivery.data.repo.menu_product.IMenuProductRepository
import com.bunbeauty.food_delivery.data.repo.user.IUserRepository

class MenuProductService(
    private val menuProductRepository: IMenuProductRepository,
    private val userRepository: IUserRepository,
) : IMenuProductService {

    override suspend fun createMenuProduct(postMenuProduct: PostMenuProduct, creatorUuid: String): GetMenuProduct? {
        val companyUuid = userRepository.getCompanyUuidByUserUuid(creatorUuid.toUuid()) ?: return null
        val insertMenuProduct = InsertMenuProduct(
            name = postMenuProduct.name,
            newPrice = postMenuProduct.newPrice,
            oldPrice = postMenuProduct.oldPrice,
            utils = postMenuProduct.utils,
            nutrition = postMenuProduct.nutrition,
            description = postMenuProduct.description,
            comboDescription = postMenuProduct.comboDescription,
            photoLink = postMenuProduct.photoLink,
            barcode = postMenuProduct.barcode,
            companyUUID = companyUuid.toUuid(),
            categoryUuids = postMenuProduct.categoryUuids.map { categoryUuid ->
                categoryUuid.toUuid()
            },
            isVisible = postMenuProduct.isVisible,
        )

        return menuProductRepository.insertMenuProduct(insertMenuProduct)
    }

    override suspend fun getMenuProductListByCompanyUuid(companyUuid: String): List<GetMenuProduct> =
        menuProductRepository.getMenuProductListByCompanyUuid(companyUuid.toUuid())

}