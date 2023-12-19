package com.bunbeauty.fooddelivery.domain.feature.city

import com.bunbeauty.fooddelivery.domain.feature.cafe.model.cafe.Cafe
import com.bunbeauty.fooddelivery.domain.feature.company.Company

class City(
    val uuid: String,
    val name: String,
    val timeZone: String,
    val isVisible: Boolean,
    val company: Company,
    val cafes: List<Cafe>,
)