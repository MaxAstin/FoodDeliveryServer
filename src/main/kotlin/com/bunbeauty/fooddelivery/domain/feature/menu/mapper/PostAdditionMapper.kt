package com.bunbeauty.fooddelivery.domain.feature.menu.mapper

import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.InsertAddition
import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.PostAddition
import java.util.*

fun PostAddition.toInsertAddition(companyUuid: UUID): InsertAddition {
    return InsertAddition(
        name = name,
        fullName = fullName,
        price = price,
        photoLink = photoLink,
        tag = tag,
        priority = priority,
        isVisible = isVisible,
        companyUuid = companyUuid
    )
}
