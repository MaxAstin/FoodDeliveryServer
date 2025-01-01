package com.bunbeauty.fooddelivery.domain.feature.city

import com.bunbeauty.fooddelivery.domain.feature.cafe.model.cafe.Cafe

class CityWithCafes(
    val city: City,
    val cafes: List<Cafe>,
)