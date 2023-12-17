package com.bunbeauty.fooddelivery.domain.feature.address.mapper

import com.bunbeauty.fooddelivery.domain.feature.address.model.InsertAddressV2
import com.bunbeauty.fooddelivery.domain.feature.address.model.PostAddressV2
import com.bunbeauty.fooddelivery.domain.toUuid

val mapPostAddressV2: PostAddressV2.(String) -> InsertAddressV2 = { userUuid ->
    InsertAddressV2(
        streetFiasId = street.fiasId,
        streetName = street.name,
        house = house,
        flat = flat,
        entrance = entrance,
        floor = floor,
        comment = comment,
        isVisible = isVisible,
        clientUserUuid = userUuid.toUuid(),
    )
}