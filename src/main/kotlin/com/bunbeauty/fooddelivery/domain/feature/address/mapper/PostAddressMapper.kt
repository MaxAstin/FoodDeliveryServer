package com.bunbeauty.fooddelivery.domain.feature.address.mapper

import com.bunbeauty.fooddelivery.domain.model.address.InsertAddress
import com.bunbeauty.fooddelivery.domain.model.address.PostAddress
import com.bunbeauty.fooddelivery.domain.toUuid

val mapPostAddress: PostAddress.(String) -> InsertAddress = { userUuid ->
    InsertAddress(
        house = house,
        flat = flat,
        entrance = entrance,
        floor = floor,
        comment = comment,
        streetUuid = streetUuid.toUuid(),
        clientUserUuid = userUuid.toUuid(),
        isVisible = isVisible,
    )
}