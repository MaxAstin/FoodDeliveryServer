package com.bunbeauty.food_delivery.routing.model

import com.bunbeauty.food_delivery.auth.JwtUser

data class Request(
    val jwtUser: JwtUser,
    val parameterMap: Map<String, String>
)
