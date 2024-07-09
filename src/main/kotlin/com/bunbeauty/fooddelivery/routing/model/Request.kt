package com.bunbeauty.fooddelivery.routing.model

import com.bunbeauty.fooddelivery.auth.JwtUser

class Request(
    val jwtUser: JwtUser
)
