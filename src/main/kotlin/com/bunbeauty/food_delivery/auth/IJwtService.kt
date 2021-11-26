package com.bunbeauty.food_delivery.auth

import com.auth0.jwt.JWTVerifier
import com.bunbeauty.food_delivery.data.model.user.GetUser
import io.ktor.auth.jwt.*

interface IJwtService {

    val verifier: JWTVerifier

    fun generateToken(user: GetUser): String
    fun configureAuth(config: JWTAuthenticationProvider.Configuration)
}