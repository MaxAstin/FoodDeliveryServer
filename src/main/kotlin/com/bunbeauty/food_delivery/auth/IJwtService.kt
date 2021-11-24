package com.bunbeauty.food_delivery.auth

import com.auth0.jwt.JWTVerifier
import io.ktor.auth.jwt.*

interface IJwtService {

    val verifier: JWTVerifier

    fun generateToken(userUuid: String): String
    fun configureAuth(config: JWTAuthenticationProvider.Configuration)
}