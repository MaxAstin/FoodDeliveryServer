package com.bunbeauty.fooddelivery.auth

import com.auth0.jwt.JWTVerifier
import com.bunbeauty.fooddelivery.data.model.Token
import com.bunbeauty.fooddelivery.data.model.client_user.GetClientUser
import com.bunbeauty.fooddelivery.data.model.user.GetUser
import io.ktor.auth.jwt.*

interface IJwtService {

    val verifier: JWTVerifier

    fun generateToken(user: GetUser): Token
    fun generateToken(clientUser: GetClientUser): Token
    fun configureAuth(config: JWTAuthenticationProvider.Configuration)
}