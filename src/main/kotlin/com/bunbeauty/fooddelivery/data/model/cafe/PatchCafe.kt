package com.bunbeauty.fooddelivery.data.model.cafe

import kotlinx.serialization.Serializable

@Serializable
class PatchCafe(
    val fromTime: Int? = null,
    val toTime: Int? = null,
    val phoneNumber: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val address: String? = null,
    val isVisible: Boolean? = null,
)