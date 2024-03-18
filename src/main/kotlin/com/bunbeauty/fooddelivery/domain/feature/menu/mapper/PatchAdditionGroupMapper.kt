package com.bunbeauty.fooddelivery.domain.feature.menu.mapper

import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.PatchAdditionGroup
import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.UpdateAdditionGroup

val mapPatchAdditionGroup: PatchAdditionGroup.() -> UpdateAdditionGroup = {
    UpdateAdditionGroup(
        name = name,
        singleChoice = singleChoice,
        priority = priority,
        isVisible = isVisible,
    )
}