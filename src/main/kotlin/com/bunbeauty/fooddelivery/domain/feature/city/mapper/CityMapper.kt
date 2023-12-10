package com.bunbeauty.fooddelivery.domain.feature.city.mapper

import com.bunbeauty.fooddelivery.domain.feature.city.City
import com.bunbeauty.fooddelivery.domain.model.city.GetCity

val mapCity: City.() -> GetCity = {
    GetCity(
        uuid = uuid,
        name = name,
        timeZone = timeZone,
        isVisible = isVisible,
    )
}