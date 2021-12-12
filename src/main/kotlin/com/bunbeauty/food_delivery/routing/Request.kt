package com.bunbeauty.food_delivery.routing

import com.bunbeauty.food_delivery.auth.JwtUser

data class Request(
    val jwtUser: JwtUser,
    val parameterList: List<Any>
)
