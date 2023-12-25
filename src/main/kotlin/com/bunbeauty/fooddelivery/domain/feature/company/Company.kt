package com.bunbeauty.fooddelivery.domain.feature.company

import com.bunbeauty.fooddelivery.domain.feature.city.CityWithCafes

class Company(
    val uuid: String,
    val name: String,
    val offset: Int,
    val citiesWithCafes: List<CityWithCafes>
)