package com.bunbeauty.fooddelivery.domain.feature.menu.mapper

import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.AdditionGroup
import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.GetAdditionGroup

val mapAdditionGroup: AdditionGroup.() -> GetAdditionGroup = {
    GetAdditionGroup(
        uuid = uuid,
        name = name,
        singleChoice = singleChoice,
        priority = priority,
        isVisible = isVisible
    )
}
