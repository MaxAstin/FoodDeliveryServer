package com.bunbeauty.fooddelivery.data.model.cafe

import kotlinx.serialization.Serializable

@Serializable
class PatchCafe(
    val fromTime: Int?,
    val toTime: Int?,
    val phoneNumber: String?,
    val latitude: Double?,
    val longitude: Double?,
    val address: String?,
    val isVisible: Boolean?,
)