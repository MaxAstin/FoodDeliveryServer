package com.bunbeauty.fooddelivery.auth

import com.auth0.jwt.JWTVerifier
import com.bunbeauty.fooddelivery.domain.feature.user.User
import com.bunbeauty.fooddelivery.domain.model.client_user.GetClientUser
import io.ktor.server.auth.jwt.*

interface IJwtService {

    val verifier: JWTVerifier

    fun generateToken(user: User): String
    fun generateToken(clientUser: GetClientUser): String
    fun configureAuth(config: JWTAuthenticationProvider.Config)
}