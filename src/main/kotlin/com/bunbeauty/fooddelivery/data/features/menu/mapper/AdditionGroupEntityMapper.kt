package com.bunbeauty.fooddelivery.data.features.menu.mapper

import com.bunbeauty.fooddelivery.data.entity.menu.AdditionGroupEntity
import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.AdditionGroup

val mapAdditionGroupEntity: AdditionGroupEntity.() -> AdditionGroup = {
    AdditionGroup(
        uuid = uuid,
        name = name,
        singleChoice = singleChoice,
        isVisible = isVisible,
        additions = additions.map(mapAdditionEntity),
    )
}