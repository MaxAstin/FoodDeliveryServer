package com.bunbeauty.fooddelivery.domain.feature.menu.mapper

import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.PatchAddition
import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.UpdateAddition

val mapPatchAddition: PatchAddition.() -> UpdateAddition = {
    UpdateAddition(
        name = name,
        fullName = fullName,
        price = price,
        photoLink = photoLink,
        priority = priority,
        isVisible = isVisible,
    )
}