package com.bunbeauty.fooddelivery.domain.feature.cafe.model.cafe

import java.util.*

class InsertCafe(
    val fromTime: Int,
    val toTime: Int,
    val offset: Int,
    val phone: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val cityUuid: UUID,
    val isVisible: Boolean
)
