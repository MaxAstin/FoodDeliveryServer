package com.bunbeauty.fooddelivery.data.model.user

import com.bunbeauty.fooddelivery.data.model.city.GetCity
import com.bunbeauty.fooddelivery.data.model.company.GetCompany
import kotlinx.serialization.Serializable

@Serializable
class GetUser(
    val uuid: String,
    val username: String,
    val passwordHash: String,
    val role: String,
    val city: GetCity,
    val company: GetCompany,
)