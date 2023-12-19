package com.bunbeauty.fooddelivery.domain.feature.city

import com.bunbeauty.fooddelivery.domain.feature.cafe.model.cafe.Cafe

class City(
    val uuid: String,
    val name: String,
    val timeZone: String,
    val isVisible: Boolean,
    val cafes: List<Cafe>,
)