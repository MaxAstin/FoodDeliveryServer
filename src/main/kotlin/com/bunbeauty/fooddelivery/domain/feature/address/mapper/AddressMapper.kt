package com.bunbeauty.fooddelivery.domain.feature.address.mapper

import com.bunbeauty.fooddelivery.domain.feature.address.model.Address
import com.bunbeauty.fooddelivery.domain.feature.address.model.GetAddress
import com.bunbeauty.fooddelivery.domain.feature.address.model.GetAddressV2

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

val mapAddressToV2: Address.() -> GetAddressV2 = {
    GetAddressV2(
        uuid = uuid,
        street = street.name,
        house = house,
        flat = flat,
        entrance = entrance,
        floor = floor,
        comment = comment,
        userUuid = userUuid,
        cityUuid = street.cityUuid,
        isVisible = isVisible,
    )
}