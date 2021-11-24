package com.bunbeauty.food_delivery.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.bunbeauty.food_delivery.data.model.user.JwtUser
import io.ktor.auth.*
import io.ktor.auth.jwt.*

class JwtService: IJwtService {

    val jwtSecret: String = System.getenv("JWT_SECRET")
    val algorithm: Algorithm = Algorithm.HMAC256(jwtSecret)
    override val verifier: JWTVerifier = JWT.require(algorithm)
        .withSubject(JWT_SUBJECT)
        .withIssuer(JWT_ISSUER)
        .build()

    override fun generateToken(userUuid: String): String {
        return JWT.create()
            .withSubject(JWT_SUBJECT)
            .withIssuer(JWT_ISSUER)
            .withClaim(USER_UUID, userUuid)
            .sign(algorithm)
    }

    override fun configureAuth(config: JWTAuthenticationProvider.Configuration) = config.run {
        verifier(verifier)
        realm = JWT_REALM
        validate { call ->
            val userUuid = call.payload.getClaim(USER_UUID).asString()

            if (userUuid != null) {
                JwtUser(userUuid)
            } else {
                null
            }
        }
    }

    companion object {
        const val JWT_SUBJECT = "auth"
        const val JWT_ISSUER = "FoodDeliveryApi"
        const val JWT_REALM = "FoodDeliveryApi"
        const val USER_UUID = "userUuid"
    }

}