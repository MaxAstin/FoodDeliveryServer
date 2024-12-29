package com.bunbeauty.fooddelivery.domain.feature.cafe.mapper.cafe

import com.bunbeauty.fooddelivery.domain.feature.cafe.model.cafe.PatchCafe
import com.bunbeauty.fooddelivery.domain.feature.cafe.model.cafe.UpdateCafe

val mapPatchCafe: PatchCafe.() -> UpdateCafe = {
    UpdateCafe(
        fromTime = fromTime,
        toTime = toTime,
        offset = offset,
        phone = phone,
        latitude = latitude,
        longitude = longitude,
        address = address,
        isVisible = isVisible
    )
}
