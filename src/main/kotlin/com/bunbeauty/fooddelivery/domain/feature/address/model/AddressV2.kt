package com.bunbeauty.fooddelivery.domain.feature.address.model

class AddressV2(
    val uuid: String,
    val street: StreetV2,
    val house: String,
    val flat: String?,
    val entrance: String?,
    val floor: String?,
    val comment: String?,
    val userUuid: String,
    val isVisible: Boolean,
)