package com.bunbeauty.fooddelivery.data.features.cafe.mapper

import com.bunbeauty.fooddelivery.data.entity.cafe.CafeEntity
import com.bunbeauty.fooddelivery.domain.feature.cafe.model.cafe.Cafe

val mapCafeEntity: CafeEntity.() -> Cafe = {
    Cafe(
        uuid = uuid,
        fromTime = fromTime,
        toTime = toTime,
        offset = offset,
        phone = phoneNumber,
        latitude = latitude,
        longitude = longitude,
        address = address,
        cityUuid = city.uuid,
        isVisible = isVisible,
    )
}