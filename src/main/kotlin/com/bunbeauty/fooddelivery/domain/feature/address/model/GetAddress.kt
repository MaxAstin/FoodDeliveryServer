package com.bunbeauty.fooddelivery.domain.feature.address.model

import com.bunbeauty.fooddelivery.domain.model.street.GetStreet
import kotlinx.serialization.Serializable

@Serializable
class GetAddress(
    val uuid: String,
    val house: String,
    val flat: String?,
    val entrance: String?,
    val floor: String?,
    val comment: String?,
    val street: GetStreet,
    val userUuid: String,
    val isVisible: Boolean
)
