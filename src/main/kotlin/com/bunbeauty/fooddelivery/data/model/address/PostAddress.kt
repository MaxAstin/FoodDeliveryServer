package com.bunbeauty.fooddelivery.data.model.address

import kotlinx.serialization.Serializable

@Serializable
class PostAddress(
    val house: String,
    val flat: String?,
    val entrance: String?,
    val floor: String?,
    val comment: String?,
    val streetUuid: String,
    val isVisible: Boolean,
)