package com.bunbeauty.food_delivery.service.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm

class AuthService : IAuthService {

    val subject = "auth"
    val issuer = "FoodDeliveryApi"
    val jwtSecret = System.getenv("JWT_SECRET")
    val algorithm = Algorithm.HMAC256(jwtSecret)
    val verifier = JWT.require(algorithm)
        .withIssuer(issuer)
        .build()

    fun generateToken(userUuid: String): String {
        val decodedJWT = verifier.verify("")
        //decodedJWT.

        return JWT.create()
            .withSubject(subject)
            .withIssuer(issuer)
            .withClaim("userUuid", userUuid)
            .sign(algorithm)
    }
}