package com.bunbeauty.fooddelivery.data.model.cafe

import java.util.UUID

class InsertCafe(
    val fromTime: Int,
    val toTime: Int,
    val phoneNumber: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val cityUuid: UUID,
    val isVisible: Boolean,
)