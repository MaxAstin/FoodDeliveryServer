package com.bunbeauty.fooddelivery.data.model.address

import com.bunbeauty.fooddelivery.data.model.street.GetStreet
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
    val userUuid: String,
    val isVisible: Boolean,
)