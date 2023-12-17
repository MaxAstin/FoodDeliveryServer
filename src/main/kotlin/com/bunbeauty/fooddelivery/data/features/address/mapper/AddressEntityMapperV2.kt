package com.bunbeauty.fooddelivery.data.features.address.mapper

import com.bunbeauty.fooddelivery.data.entity.AddressEntityV2
import com.bunbeauty.fooddelivery.domain.feature.address.model.AddressV2
import com.bunbeauty.fooddelivery.domain.feature.address.model.StreetV2

val mapAddressEntityV2: AddressEntityV2.() -> AddressV2 = {
    AddressV2(
        uuid = uuid,
        street = StreetV2(
            fiasId = streetFiasId,
            name = streetName,
        ),
        house = house,
        flat = flat,
        entrance = entrance,
        floor = floor,
        comment = comment,
        userUuid = clientUser.uuid,
        cityUuid = city.uuid,
        isVisible = isVisible,
    )
}