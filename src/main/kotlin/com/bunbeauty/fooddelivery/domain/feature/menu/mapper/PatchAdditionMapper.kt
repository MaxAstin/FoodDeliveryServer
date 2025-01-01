package com.bunbeauty.fooddelivery.domain.feature.menu.mapper

import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.PatchAddition
import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.UpdateAddition

fun PatchAddition.toUpdateAddition(): UpdateAddition {
    return UpdateAddition(
        name = name,
        fullName = fullName,
        price = price,
        photoLink = photoLink,
        tag = tag,
        priority = priority,
        isVisible = isVisible,
    )
}