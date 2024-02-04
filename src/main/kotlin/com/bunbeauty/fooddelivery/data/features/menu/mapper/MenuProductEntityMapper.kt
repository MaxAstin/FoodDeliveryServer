package com.bunbeauty.fooddelivery.data.features.menu.mapper

import com.bunbeauty.fooddelivery.data.entity.menu.MenuProductEntity
import com.bunbeauty.fooddelivery.data.entity.menu.MenuProductWithAdditionEntity
import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.Addition
import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.AdditionGroup
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
        additionGroups = emptyList(),
    )
}

val mapMenuProductWithAdditionEntityListToMenuProduct: List<MenuProductWithAdditionEntity>.() -> MenuProduct = {
    val menuProduct = first().menuProduct.mapMenuProductEntity()
    val additionGroups = groupBy { mapMenuProductWithAdditionEntity ->
        mapMenuProductWithAdditionEntity.additionGroup.uuid
    }.values.map(mapMenuProductWithAdditionEntityListToAdditionGroup)

    menuProduct.copy(additionGroups = additionGroups)
}

private val mapMenuProductWithAdditionEntityListToAdditionGroup: List<MenuProductWithAdditionEntity>.() -> AdditionGroup =
    {
        val additionGroupEntity = first().additionGroup
        AdditionGroup(
            uuid = additionGroupEntity.uuid,
            name = additionGroupEntity.name,
            singleChoice = additionGroupEntity.singleChoice,
            priority = additionGroupEntity.priority,
            isVisible = additionGroupEntity.isVisible && any { menuProductWithAdditionEntity ->
                menuProductWithAdditionEntity.isVisible &&
                        menuProductWithAdditionEntity.addition.isVisible
            },
            additions = map(mapMenuProductWithAdditionEntityToAddition),
        )
    }

private val mapMenuProductWithAdditionEntityToAddition: MenuProductWithAdditionEntity.() -> Addition = {
    Addition(
        uuid = addition.uuid,
        name = addition.name,
        fullName = addition.fullName,
        isSelected = isSelected,
        price = addition.price,
        photoLink = addition.photoLink,
        priority = addition.priority,
        isVisible = isVisible && addition.isVisible,
    )
}