package com.bunbeauty.fooddelivery.domain.feature.address.model

import java.util.*

class InsertAddressV2(
    val streetFiasId: String,
    val streetName: String,
    val house: String,
    val flat: String?,
    val entrance: String?,
    val floor: String?,
    val comment: String?,
    val isVisible: Boolean,
    val clientUserUuid: UUID,
    val cityUuid: UUID,
)