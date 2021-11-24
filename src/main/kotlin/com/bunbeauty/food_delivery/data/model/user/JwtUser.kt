package com.bunbeauty.food_delivery.data.model.user

import io.ktor.auth.*

data class JwtUser(
    val userUuid: String,
) : Principal
