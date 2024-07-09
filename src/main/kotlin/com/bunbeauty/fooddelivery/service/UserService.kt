package com.bunbeauty.fooddelivery.service

import at.favre.lib.crypto.bcrypt.BCrypt.MIN_COST
import com.bunbeauty.fooddelivery.auth.IJwtService
import com.bunbeauty.fooddelivery.data.enums.UserRole
import com.bunbeauty.fooddelivery.data.features.user.UserRepository
import com.bunbeauty.fooddelivery.domain.feature.user.mapper.mapUser
import com.bunbeauty.fooddelivery.domain.model.user.*
import com.bunbeauty.fooddelivery.domain.toUuid
import com.toxicbakery.bcrypt.Bcrypt
import com.toxicbakery.bcrypt.Bcrypt.verify

class UserService(
    private val userRepository: UserRepository,
    private val jwtService: IJwtService,
) {

    suspend fun createUser(postUser: PostUser): GetUser {
        val passwordHash = String(Bcrypt.hash(postUser.password, MIN_COST))
        val insertUser = InsertUser(
            username = postUser.username.lowercase(),
            passwordHash = passwordHash,
            role = UserRole.findByRoleName(postUser.role),
            cityUuid = postUser.cityUuid.toUuid()
        )

        return userRepository.insertUser(insertUser).mapUser()
    }

    suspend fun login(postUserAuth: PostUserAuth): UserAuthResponse? {
        return userRepository.getUserByUsername(postUserAuth.username.lowercase())?.let { user ->
            if (verify(postUserAuth.password, user.passwordHash.toByteArray())) {
                val token = jwtService.generateToken(user)
                UserAuthResponse(
                    token = token,
                    cityUuid = user.cityWithCafes.city.uuid,
                    companyUuid = user.companyUuid
                )
            } else {
                println(
                    "Incorrect password ${postUserAuth.password} " +
                            "password hash [${String(Bcrypt.hash(postUserAuth.password, MIN_COST))}] " +
                            "actual hash [${user.passwordHash}]"
                )
                null
            }
        }
    }
}