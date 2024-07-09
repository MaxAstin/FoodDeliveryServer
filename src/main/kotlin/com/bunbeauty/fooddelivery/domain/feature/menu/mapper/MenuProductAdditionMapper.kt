package com.bunbeauty.fooddelivery.domain.feature.menu.mapper

import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.GetMenuProductAddition
import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.MenuProductAddition

val mapMenuProductAddition: MenuProductAddition.() -> GetMenuProductAddition = {
    GetMenuProductAddition(
        uuid = uuid,
        name = name,
        fullName = fullName,
        isSelected = isSelected,
        price = price,
        photoLink = photoLink,
        priority = priority,
        isVisible = isVisible,
    )
}