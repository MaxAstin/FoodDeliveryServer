package com.bunbeauty.fooddelivery.domain.feature.menu.mapper

import com.bunbeauty.fooddelivery.domain.feature.menu.model.PatchMenuProduct
import com.bunbeauty.fooddelivery.domain.feature.menu.model.UpdateMenuProduct
import com.bunbeauty.fooddelivery.domain.mapUuid

val mapPatchMenuProduct: PatchMenuProduct.() -> UpdateMenuProduct = {
    UpdateMenuProduct(
        name = name,
        newPrice = newPrice,
        oldPrice = oldPrice,
        utils = utils,
        nutrition = nutrition,
        description = description,
        comboDescription = comboDescription,
        photoLink = photoLink,
        barcode = barcode,
        categoryUuids = categoryUuids?.map(mapUuid),
        isVisible = isVisible,
    )
}