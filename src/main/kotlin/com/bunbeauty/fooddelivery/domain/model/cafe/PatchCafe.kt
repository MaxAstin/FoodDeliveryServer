package com.bunbeauty.fooddelivery.domain.model.cafe

import kotlinx.serialization.Serializable

@Serializable
class PatchCafe(
    val fromTime: Int? = null,
    val toTime: Int? = null,
    val offset: Int? = null,
    val phone: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val address: String? = null,
    val isVisible: Boolean? = null,
)