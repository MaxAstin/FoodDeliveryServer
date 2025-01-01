package com.bunbeauty.fooddelivery.domain.feature.address.mapper

import com.bunbeauty.fooddelivery.domain.feature.address.model.AddressInfoV2
import com.bunbeauty.fooddelivery.domain.feature.address.model.InsertAddressV2
import com.bunbeauty.fooddelivery.domain.feature.address.model.InsertStreetV2
import com.bunbeauty.fooddelivery.domain.feature.address.model.PostAddressV2

val mapPostAddressV2: PostAddressV2.(AddressInfoV2) -> InsertAddressV2 = { addressInfo ->
    InsertAddressV2(
        street = InsertStreetV2(
            fiasId = street.fiasId,
            name = street.name,
            latitude = addressInfo.streetLatitude,
            longitude = addressInfo.streetLongitude
        ),
        house = house,
        flat = flat,
        entrance = entrance,
        floor = floor,
        comment = comment,
        isVisible = isVisible,
        clientUserUuid = addressInfo.userUuid,
        cityUuid = cityUuid
    )
}
