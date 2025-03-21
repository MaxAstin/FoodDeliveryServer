package com.bunbeauty.fooddelivery.data.features.menu.mapper

import com.bunbeauty.fooddelivery.data.entity.menu.AdditionEntity
import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.Addition

fun AdditionEntity.toAddition(): Addition {
    return Addition(
        uuid = uuid,
        name = name,
        fullName = fullName,
        price = price,
        photoLink = photoLink,
        tag = tag,
        priority = priority,
        isVisible = isVisible,
        companyUuid = company.uuid
    )
}
