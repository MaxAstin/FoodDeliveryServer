package com.bunbeauty.fooddelivery.data.model.cafe

import kotlinx.serialization.Serializable

@Serializable
data class PostCafe(
    val fromTime: Int,
    val toTime: Int,
    val phoneNumber: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val cityUuid: String,
    val isVisible: Boolean,
)