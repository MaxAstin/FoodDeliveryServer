package com.bunbeauty.fooddelivery.domain.feature.menu.mapper

import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.GetMenuProductAdditionGroup
import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.MenuProductAdditionGroup
import com.bunbeauty.fooddelivery.domain.feature.menu.model.menuproduct.GetMenuProduct
import com.bunbeauty.fooddelivery.domain.feature.menu.model.menuproduct.MenuProduct

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
        isVisible = isVisible,
        categories = categories.map(mapCategory),
        additionGroups = additionGroups.map(mapMenuProductAdditionGroup),
    )
}

private val mapMenuProductAdditionGroup: MenuProductAdditionGroup.() -> GetMenuProductAdditionGroup = {
    GetMenuProductAdditionGroup(
        uuid = uuid,
        name = name,
        singleChoice = singleChoice,
        priority = priority,
        isVisible = isVisible,
        additions = additions.map(mapMenuProductAddition),
    )
}