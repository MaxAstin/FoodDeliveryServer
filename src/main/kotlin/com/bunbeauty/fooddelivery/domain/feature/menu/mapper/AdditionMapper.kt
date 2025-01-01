package com.bunbeauty.fooddelivery.domain.feature.menu.mapper

import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.Addition
import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.GetAddition

fun Addition.toGetAddition(): GetAddition {
    return GetAddition(
        uuid = uuid,
        name = name,
        fullName = fullName,
        price = price,
        photoLink = photoLink,
        tag = tag,
        priority = priority,
        isVisible = isVisible
    )
}
