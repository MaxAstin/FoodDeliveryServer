package com.bunbeauty.fooddelivery.data.model.cafe

import kotlinx.serialization.Serializable

@Serializable
class PostCafe(
    val fromTime: Int,
    val toTime: Int,
    val phone: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val cityUuid: String,
    val isVisible: Boolean,
)