package com.bunbeauty.fooddelivery.data.model.city

import com.bunbeauty.fooddelivery.data.model.company.GetCompany
import kotlinx.serialization.Serializable

@Serializable
data class GetCity(
    val uuid: String,
    val name: String,
    val timeZone: String,
    val company: GetCompany,
    val isVisible: Boolean,
)