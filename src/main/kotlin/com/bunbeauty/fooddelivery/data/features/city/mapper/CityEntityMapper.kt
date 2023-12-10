package com.bunbeauty.fooddelivery.data.features.city.mapper

import com.bunbeauty.fooddelivery.data.entity.CityEntity
import com.bunbeauty.fooddelivery.domain.feature.city.City

val mapCityEntity: CityEntity.() -> City = {
    City(
        uuid = uuid,
        name = name,
        timeZone = timeZone,
        isVisible = isVisible,
    )
}