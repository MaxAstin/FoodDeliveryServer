package com.bunbeauty.fooddelivery.auth

import com.auth0.jwt.JWTVerifier
import com.bunbeauty.fooddelivery.domain.feature.clientuser.model.ClientUser
import com.bunbeauty.fooddelivery.domain.feature.user.model.domain.User
import io.ktor.server.auth.jwt.*

interface IJwtService {

    val verifier: JWTVerifier

    fun generateToken(user: User): String
    fun generateToken(clientUser: ClientUser): String
    fun configureAuth(config: JWTAuthenticationProvider.Config)
}