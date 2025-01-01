package com.bunbeauty.fooddelivery.domain.feature.address.mapper

import com.bunbeauty.fooddelivery.domain.model.street.InsertStreet
import com.bunbeauty.fooddelivery.domain.model.street.PostStreet
import com.bunbeauty.fooddelivery.domain.toUuid

val mapPostStreet: PostStreet.() -> InsertStreet = {
    InsertStreet(
        name = name,
        cafeUuid = cafeUuid.toUuid(),
        isVisible = isVisible
    )
}
