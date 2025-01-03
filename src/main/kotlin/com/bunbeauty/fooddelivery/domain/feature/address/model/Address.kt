package com.bunbeauty.fooddelivery.domain.feature.address.model

class Address(
    val uuid: String,
    val house: String,
    val flat: String?,
    val entrance: String?,
    val floor: String?,
    val comment: String?,
    val street: Street,
    val cityUuid: String,
    val userUuid: String,
    val isVisible: Boolean
)
