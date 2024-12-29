package com.bunbeauty.fooddelivery.domain.feature.address.model

class InsertAddressV2(
    val street: InsertStreetV2,
    val house: String,
    val flat: String?,
    val entrance: String?,
    val floor: String?,
    val comment: String?,
    val isVisible: Boolean,
    val clientUserUuid: String,
    val cityUuid: String
)

class InsertStreetV2(
    val fiasId: String,
    val name: String,
    val latitude: Double,
    val longitude: Double
)
