package com.bunbeauty.fooddelivery.domain.feature.address.mapper

import com.bunbeauty.fooddelivery.domain.feature.address.model.Address
import com.bunbeauty.fooddelivery.domain.model.address.GetAddress

val mapAddress: Address.() -> GetAddress = {
    GetAddress(
        uuid = uuid,
        house = house,
        flat = flat,
        entrance = entrance,
        floor = floor,
        comment = comment,
        street = street.mapStreet(),
        userUuid = userUuid,
        isVisible = isVisible,
    )
}