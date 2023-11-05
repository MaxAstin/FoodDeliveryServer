package com.bunbeauty.fooddelivery.domain.model.street

import kotlinx.serialization.Serializable

@Serializable
class GetStreet(
    val uuid: String,
    val name: String,
    val cityUuid: String,
    val cafeUuid: String,
    val isVisible: Boolean,
)
