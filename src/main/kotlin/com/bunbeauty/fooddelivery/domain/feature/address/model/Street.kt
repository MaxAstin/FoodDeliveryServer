package com.bunbeauty.fooddelivery.domain.feature.address.model

class Street(
    val uuid: String,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val isVisible: Boolean,
    val cityUuid: String,
    val cafeUuid: String,
    val companyUuid: String,
)