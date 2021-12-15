package com.bunbeauty.fooddelivery.data.model.street

import java.util.*

data class InsertStreet(
    val name: String,
    val cafeUuid: UUID,
    val isVisible: Boolean,
)
