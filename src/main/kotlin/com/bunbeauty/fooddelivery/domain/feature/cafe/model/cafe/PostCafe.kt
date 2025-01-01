package com.bunbeauty.fooddelivery.domain.feature.cafe.model.cafe

import kotlinx.serialization.Serializable

@Serializable
class PostCafe(
    val fromTime: Int,
    val toTime: Int,
    val offset: Int,
    val phone: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val cityUuid: String,
    val isVisible: Boolean,
)