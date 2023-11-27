package com.bunbeauty.fooddelivery.domain.feature.menu.mapper

import com.bunbeauty.fooddelivery.domain.feature.menu.model.GetMenuProduct
import com.bunbeauty.fooddelivery.domain.feature.menu.model.MenuProduct

val mapMenuProduct: MenuProduct.() -> GetMenuProduct = {
    GetMenuProduct(
        uuid = uuid,
        name = name,
        newPrice = newPrice,
        oldPrice = oldPrice,
        utils = utils,
        nutrition = nutrition,
        description = description,
        comboDescription = comboDescription,
        photoLink = photoLink,
        barcode = barcode,
        isRecommended = isRecommended,
        categories = categories.map(mapCategory),
        isVisible = isVisible,
    )
}