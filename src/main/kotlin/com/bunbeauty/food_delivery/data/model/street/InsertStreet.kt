package com.bunbeauty.food_delivery.data.model.street

import java.util.*

data class InsertStreet(
    val name: String,
    val cafeUuid: UUID,
    val isVisible: Boolean,
)
