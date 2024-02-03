package com.bunbeauty.fooddelivery.domain.feature.address.model

class Suggestion(
    val fiasId: String,
    val street: String,
    val settlement: String?,
    val city: String?,
    val latitude: Double,
    val longitude: Double,
)