package com.bunbeauty.fooddelivery.domain.feature.company

import com.bunbeauty.fooddelivery.domain.feature.city.City

class Company(
    val uuid: String,
    val name: String,
    val offset: Int,
    val cities: List<City>
)