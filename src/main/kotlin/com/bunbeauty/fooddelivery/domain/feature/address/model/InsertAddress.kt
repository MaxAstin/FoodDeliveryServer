package com.bunbeauty.fooddelivery.domain.feature.address.model

import java.util.*

class InsertAddress(
    val house: String,
    val flat: String?,
    val entrance: String?,
    val floor: String?,
    val comment: String?,
    val streetUuid: UUID,
    val clientUserUuid: UUID,
    val isVisible: Boolean
)
