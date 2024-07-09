package com.bunbeauty.fooddelivery.domain.feature.address.mapper

import com.bunbeauty.fooddelivery.domain.feature.address.model.Street
import com.bunbeauty.fooddelivery.domain.model.street.GetStreet

val mapStreet: Street.() -> GetStreet = {
    GetStreet(
        uuid = uuid,
        name = name,
        cityUuid = cityUuid,
        cafeUuid = cafeUuid,
        isVisible = isVisible,
    )
}