package com.bunbeauty.fooddelivery.service.user

import at.favre.lib.crypto.bcrypt.BCrypt.MIN_COST
import com.bunbeauty.fooddelivery.auth.IJwtService
import com.bunbeauty.fooddelivery.data.enums.UserRole
import com.bunbeauty.fooddelivery.data.ext.toUuid
import com.bunbeauty.fooddelivery.data.model.Token
import com.bunbeauty.fooddelivery.data.model.user.GetUser
import com.bunbeauty.fooddelivery.data.model.user.InsertUser
import com.bunbeauty.fooddelivery.data.model.user.PostUserAuth
import com.bunbeauty.fooddelivery.data.model.user.PostUser
import com.bunbeauty.fooddelivery.data.repo.user.IUserRepository
import com.toxicbakery.bcrypt.Bcrypt
import com.toxicbakery.bcrypt.Bcrypt.verify

class UserService(private val userRepository: IUserRepository, private val jwtService: IJwtService) : IUserService {

    override suspend fun createUser(postUser: PostUser): GetUser {
        val passwordHash = String(Bcrypt.hash(postUser.password, MIN_COST))
        val insertUser = InsertUser(
            username = postUser.username,
            passwordHash = passwordHash,
            role = UserRole.findByRoleName(postUser.role),
            cityUuid = postUser.cityUuid.toUuid(),
        )

        return userRepository.insertUser(insertUser)
    }

    override suspend fun getToken(postUserAuth: PostUserAuth): Token? {
        val user = userRepository.getUserByUsername(postUserAuth.username) ?: return null
        return if (verify(postUserAuth.password, user.passwordHash.toByteArray())) {
            jwtService.generateToken(user)
        } else {
            null
        }
    }
}