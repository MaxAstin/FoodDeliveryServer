package com.bunbeauty.food_delivery.data.model.street

import kotlinx.serialization.Serializable

@Serializable
data class GetStreet(
    val uuid: String,
    val name: String,
    val cafeUuid: String,
    val isVisible: Boolean,
)
