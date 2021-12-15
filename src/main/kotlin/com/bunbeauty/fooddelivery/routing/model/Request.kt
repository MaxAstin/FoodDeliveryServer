package com.bunbeauty.fooddelivery.routing.model

import com.bunbeauty.fooddelivery.auth.JwtUser

data class Request(
    val jwtUser: JwtUser,
    val parameterMap: Map<String, String>
)
