package com.bunbeauty.fooddelivery.domain.feature.menu.mapper

import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.InsertAddition
import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.PostAddition
import java.util.*

val mapPostAddition: PostAddition.(UUID) -> InsertAddition = { companyUuid ->
    InsertAddition(
        name = name,
        fullName = fullName,
        price = price,
        photoLink = photoLink,
        priority = priority,
        isVisible = isVisible,
        companyUuid = companyUuid,
    )
}