package com.bunbeauty.fooddelivery.domain.feature.menu.mapper

import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.Addition
import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.GetAddition

val mapAddition: Addition.() -> GetAddition = {
    GetAddition(
        uuid = uuid,
        name = name,
        isSelected = isSelected,
        price = price,
        photoLink = photoLink,
        isVisible = isVisible,
    )
}