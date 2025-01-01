package com.bunbeauty.fooddelivery.domain.feature.cafe.mapper.cafe

import com.bunbeauty.fooddelivery.domain.feature.cafe.model.cafe.Cafe
import com.bunbeauty.fooddelivery.domain.feature.cafe.model.cafe.GetCafe

val mapCafe: Cafe.() -> GetCafe = {
    GetCafe(
        uuid = uuid,
        fromTime = fromTime,
        toTime = toTime,
        offset = offset,
        phone = phone,
        latitude = latitude,
        longitude = longitude,
        address = address,
        cityUuid = cityUuid,
        isVisible = isVisible,
    )
}