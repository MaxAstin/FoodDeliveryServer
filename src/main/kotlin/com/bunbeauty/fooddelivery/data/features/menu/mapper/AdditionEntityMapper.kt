package com.bunbeauty.fooddelivery.data.features.menu.mapper

import com.bunbeauty.fooddelivery.data.entity.menu.AdditionEntity
import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.Addition

val mapAdditionEntity: AdditionEntity.() -> Addition = {
    Addition(
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