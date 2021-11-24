package com.bunbeauty.food_delivery.data.model.company

import kotlinx.serialization.Serializable

@Serializable
data class GetCompany(
    val uuid: String,
    val name: String,
)