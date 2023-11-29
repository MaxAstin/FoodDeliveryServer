package com.bunbeauty.fooddelivery.data.features.menu.mapper

import com.bunbeauty.fooddelivery.data.entity.menu.MenuProductEntity
import com.bunbeauty.fooddelivery.domain.feature.menu.model.menuproduct.MenuProduct

val mapMenuProductEntity: MenuProductEntity.() -> MenuProduct = {
    MenuProduct(
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
        isVisible = isVisible,
        categories = categories.map(mapCategoryEntity),
        additionGroups = additionGroups.map(mapAdditionGroupEntity),
    )
}