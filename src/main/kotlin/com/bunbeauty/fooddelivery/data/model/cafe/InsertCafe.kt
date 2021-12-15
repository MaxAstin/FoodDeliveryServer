package com.bunbeauty.fooddelivery.data.model.cafe

import java.util.UUID

data class InsertCafe(
    val fromTime: Int,
    val toTime: Int,
    val phone: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val cityUuid: UUID,
    val isVisible: Boolean,
)