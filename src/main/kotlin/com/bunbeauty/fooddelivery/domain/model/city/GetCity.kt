package com.bunbeauty.fooddelivery.domain.model.city

import com.bunbeauty.fooddelivery.domain.model.company.GetCompany
import kotlinx.serialization.Serializable

@Serializable
class GetCity(
    val uuid: String,
    val name: String,
    val timeZone: String,
    val company: GetCompany,
    val isVisible: Boolean,
)