package com.bunbeauty.food_delivery.data.model.address

import java.util.*

data class InsertAddress(
    val house: String,
    val flat: String?,
    val entrance: String?,
    val floor: String?,
    val comment: String?,
    val streetUuid: UUID,
    val userUuid: UUID,
    val isVisible: Boolean,
)