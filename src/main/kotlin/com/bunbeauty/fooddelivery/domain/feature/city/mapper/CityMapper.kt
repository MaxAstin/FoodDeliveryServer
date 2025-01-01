package com.bunbeauty.fooddelivery.domain.feature.city.mapper

import com.bunbeauty.fooddelivery.domain.feature.city.City
import com.bunbeauty.fooddelivery.domain.feature.city.CityWithCafes
import com.bunbeauty.fooddelivery.domain.model.city.GetCity

val mapCity: City.() -> GetCity = {
    GetCity(
        uuid = uuid,
        name = name,
        timeZone = timeZone,
        isVisible = isVisible,
    )
}

val mapCityWithCafes: CityWithCafes.() -> GetCity = {
    GetCity(
        uuid = city.uuid,
        name = city.name,
        timeZone = city.timeZone,
        isVisible = city.isVisible,
    )
}