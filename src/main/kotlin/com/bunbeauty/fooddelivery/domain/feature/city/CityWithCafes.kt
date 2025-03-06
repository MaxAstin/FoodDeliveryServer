package com.bunbeauty.fooddelivery.domain.feature.city

import com.bunbeauty.fooddelivery.domain.feature.cafe.model.cafe.CafeWithZones

class CityWithCafes(
    val city: City,
    val cafeWithZones: List<CafeWithZones>
)
