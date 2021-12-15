package com.bunbeauty.fooddelivery.auth

import com.auth0.jwt.JWTVerifier
import com.bunbeauty.fooddelivery.data.model.client_user.GetClientUser
import com.bunbeauty.fooddelivery.data.model.user.GetUser
import io.ktor.auth.jwt.*

interface IJwtService {

    val verifier: JWTVerifier

    fun generateToken(user: GetUser): String
    fun generateToken(clientUser: GetClientUser): String
    fun configureAuth(config: JWTAuthenticationProvider.Configuration)
}