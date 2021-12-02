package com.bunbeauty.food_delivery.data.model.address

import com.bunbeauty.food_delivery.data.model.street.GetStreet
import kotlinx.serialization.Serializable

@Serializable
data class GetAddress(
    val uuid: String,
    val house: String,
    val flat: String?,
    val entrance: String?,
    val floor: String?,
    val comment: String?,
    val street: GetStreet,
    val isVisible: Boolean,
)