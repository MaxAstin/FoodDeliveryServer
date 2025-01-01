package com.bunbeauty.fooddelivery.domain.feature.cafe.mapper.cafe

import com.bunbeauty.fooddelivery.domain.feature.cafe.model.cafe.InsertCafe
import com.bunbeauty.fooddelivery.domain.feature.cafe.model.cafe.PostCafe
import com.bunbeauty.fooddelivery.domain.toUuid

val mapPostCafe: PostCafe.() -> InsertCafe = {
    InsertCafe(
        fromTime = fromTime,
        toTime = toTime,
        offset = offset,
        phone = phone,
        latitude = latitude,
        longitude = longitude,
        address = address,
        cityUuid = cityUuid.toUuid(),
        isVisible = isVisible
    )
}
