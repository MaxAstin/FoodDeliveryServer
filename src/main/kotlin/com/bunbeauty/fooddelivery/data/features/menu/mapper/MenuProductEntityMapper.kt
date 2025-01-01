package com.bunbeauty.fooddelivery.data.features.menu.mapper

import com.bunbeauty.fooddelivery.data.entity.menu.MenuProductEntity
import com.bunbeauty.fooddelivery.data.entity.menu.MenuProductWithAdditionGroupWithAdditionEntity
import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.MenuProductAddition
import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.MenuProductAdditionGroup
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
        companyUuid = company.uuid,
        categories = categories.map(mapCategoryEntity),
        additionGroups = emptyList()
    )
}

val mapToMenuProduct: List<MenuProductWithAdditionGroupWithAdditionEntity>.() -> MenuProduct = {
    val menuProduct = first().menuProductWithAdditionGroup.menuProduct.mapMenuProductEntity()
    val additionGroups = groupBy {
        it.menuProductWithAdditionGroup.uuid
    }.values.map(mapToMenuProductAdditionGroup)

    menuProduct.copy(additionGroups = additionGroups)
}

private val mapToMenuProductAdditionGroup: List<MenuProductWithAdditionGroupWithAdditionEntity>.() -> MenuProductAdditionGroup = {
    val menuProductWithAdditionGroup = first().menuProductWithAdditionGroup
    val additionGroup = menuProductWithAdditionGroup.additionGroup
    MenuProductAdditionGroup(
        uuid = menuProductWithAdditionGroup.uuid,
        name = additionGroup.name,
        singleChoice = additionGroup.singleChoice,
        priority = additionGroup.priority,
        isVisible = additionGroup.isVisible && any {
            it.isVisible && it.addition.isVisible
        },
        additions = map(mapToMenuProductAddition)
    )
}

private val mapToMenuProductAddition: MenuProductWithAdditionGroupWithAdditionEntity.() -> MenuProductAddition = {
    MenuProductAddition(
        uuid = uuid,
        name = addition.name,
        fullName = addition.fullName,
        isSelected = isSelected,
        price = addition.price,
        photoLink = addition.photoLink,
        priority = addition.priority,
        isVisible = isVisible && addition.isVisible
    )
}
