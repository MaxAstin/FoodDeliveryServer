package com.bunbeauty.fooddelivery.domain.feature.address.model

import kotlinx.serialization.Serializable

@Serializable
class GetAddressV2(
    val uuid: String,
    val street: String,
    val house: String,
    val flat: String?,
    val entrance: String?,
    val floor: String?,
    val comment: String?,
    val minOrderCost: Int?,
    val normalDeliveryCost: Int,
    val forLowDeliveryCost: Int?,
    val lowDeliveryCost: Int?,
    val userUuid: String,
    val cityUuid: String,
    val isVisible: Boolean
)
