package com.bunbeauty.fooddelivery.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.bunbeauty.fooddelivery.data.Constants.JWT_SECRET
import com.bunbeauty.fooddelivery.data.enums.UserRole
import com.bunbeauty.fooddelivery.domain.feature.clientuser.model.ClientUser
import com.bunbeauty.fooddelivery.domain.feature.user.User
import io.ktor.server.auth.jwt.*

class JwtService : IJwtService {

    val jwtSecret: String = System.getenv(JWT_SECRET)
    val algorithm: Algorithm = Algorithm.HMAC256(jwtSecret)
    override val verifier: JWTVerifier = JWT.require(algorithm)
        .withSubject(JWT_SUBJECT)
        .withIssuer(JWT_ISSUER)
        .build()

    override fun generateToken(clientUser: ClientUser): String {
        return JWT.create()
            .withSubject(JWT_SUBJECT)
            .withIssuer(JWT_ISSUER)
            .withClaim(USER_UUID, clientUser.uuid)
            .withClaim(USER_ROLE, UserRole.CLIENT.roleName)
            .sign(algorithm)
    }

    override fun generateToken(user: User): String {
        return JWT.create()
            .withSubject(JWT_SUBJECT)
            .withIssuer(JWT_ISSUER)
            .withClaim(USER_UUID, user.uuid)
            .withClaim(USER_ROLE, user.role)
            .sign(algorithm)
    }

    override fun configureAuth(config: JWTAuthenticationProvider.Config) = config.run {
        verifier(verifier)
        realm = JWT_REALM
        validate { call ->
            val uuid = call.payload.getClaim(USER_UUID).asString()
            val role = call.payload.getClaim(USER_ROLE).asString()

            if (uuid != null && role != null) {
                JwtUser(uuid, role)
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
        const val USER_ROLE = "userRole"
    }

}