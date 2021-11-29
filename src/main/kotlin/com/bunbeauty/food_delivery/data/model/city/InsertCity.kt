package com.bunbeauty.food_delivery.data.model.city

import java.util.*

data class InsertCity(
    val name: String,
    val offset: Int,
    val company: UUID,
    val isVisible: Boolean,
)