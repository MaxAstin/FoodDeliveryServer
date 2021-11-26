package com.bunbeauty.food_delivery.service.user

import at.favre.lib.crypto.bcrypt.BCrypt.MIN_COST
import com.bunbeauty.food_delivery.auth.IJwtService
import com.bunbeauty.food_delivery.data.enums.UserRole
import com.bunbeauty.food_delivery.data.ext.toUuid
import com.bunbeauty.food_delivery.data.model.user.GetUser
import com.bunbeauty.food_delivery.data.model.user.InsertUser
import com.bunbeauty.food_delivery.data.model.user.PostAuth
import com.bunbeauty.food_delivery.data.model.user.PostUser
import com.bunbeauty.food_delivery.data.repo.user.IUserRepository
import com.toxicbakery.bcrypt.Bcrypt
import com.toxicbakery.bcrypt.Bcrypt.verify

class UserService(private val userRepository: IUserRepository, private val jwtService: IJwtService) : IUserService {

    override suspend fun createUser(postUser: PostUser): GetUser {
        val passwordHash = String(Bcrypt.hash(postUser.password, MIN_COST))
        val insertUser = InsertUser(
            username = postUser.username,
            passwordHash = passwordHash,
            role = UserRole.findByRoleName(postUser.role),
            companyUuid = postUser.companyUuid.toUuid(),
        )

        return userRepository.insertUser(insertUser)
    }

    override suspend fun getToken(postAuth: PostAuth): String? {
        val user = userRepository.getUserByUsername(postAuth.username) ?: return null
        return if (verify(postAuth.password, user.passwordHash.toByteArray())) {
            jwtService.generateToken(user)
        } else {
            null
        }
    }
}