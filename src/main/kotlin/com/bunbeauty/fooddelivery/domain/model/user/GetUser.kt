package com.bunbeauty.fooddelivery.domain.model.user

import com.bunbeauty.fooddelivery.domain.model.city.GetCity
import kotlinx.serialization.Serializable

@Serializable
class GetUser(
    val uuid: String,
    val username: String,
    val passwordHash: String,
    val role: String,
    val city: GetCity,
)