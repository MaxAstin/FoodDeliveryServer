package com.bunbeauty.food_delivery.data.model.user

import com.bunbeauty.food_delivery.data.model.company.GetCompany
import kotlinx.serialization.Serializable

@Serializable
data class GetUser(
    val uuid: String,
    val username: String,
    val passwordHash: String,
    val company: GetCompany,
    val role: String,
)