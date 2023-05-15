package com.bunbeauty.fooddelivery.data.model.address

import java.util.UUID

class InsertAddress(
    val house: String,
    val flat: String?,
    val entrance: String?,
    val floor: String?,
    val comment: String?,
    val streetUuid: UUID,
    val clientUserUuid: UUID,
    val isVisible: Boolean,
)