package com.bunbeauty.fooddelivery.domain.feature.address.mapper

import com.bunbeauty.fooddelivery.domain.feature.address.model.AddressV2
import com.bunbeauty.fooddelivery.domain.feature.address.model.GetAddressV2

val mapAddressV2: AddressV2.() -> GetAddressV2 = {
    GetAddressV2(
        uuid = uuid,
        street = street.name,
        house = house,
        flat = flat,
        entrance = entrance,
        floor = floor,
        comment = comment,
        userUuid = userUuid,
        cityUuid = cityUuid,
        isVisible = isVisible,
    )
}