package com.bunbeauty.food_delivery.service.user

import at.favre.lib.crypto.bcrypt.BCrypt.MIN_COST
import com.bunbeauty.food_delivery.auth.IJwtService
import com.bunbeauty.food_delivery.data.entity.conpany.CompanyEntity
import com.bunbeauty.food_delivery.data.entity.UserEntity
import com.bunbeauty.food_delivery.data.enums.UserRole
import com.bunbeauty.food_delivery.data.model.user.GetUser
import com.bunbeauty.food_delivery.data.model.user.PostAuth
import com.bunbeauty.food_delivery.data.model.user.PostUser
import com.bunbeauty.food_delivery.data.repo.user.IUserRepository
import com.bunbeauty.food_delivery.data.table.UserTable
import com.toxicbakery.bcrypt.Bcrypt
import com.toxicbakery.bcrypt.Bcrypt.verify
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class UserService(private val userRepository: IUserRepository, private val jwtService: IJwtService) : IUserService {

    override suspend fun createUser(creatorUuid: String, postUser: PostUser): GetUser? {
        val creator = userRepository.getUserByUuid(creatorUuid) ?: return null
        if (creator.role != UserRole.ADMIN) {
            return null
        }

        return createUser(postUser)
    }

    override suspend fun createUser(postUser: PostUser): GetUser {
        val uuid = UUID.randomUUID().toString()
        val entityID = EntityID(uuid, UserTable)
        val passwordHash = Bcrypt.hash(postUser.password, MIN_COST).toString()
        val userEntity = UserEntity(entityID).apply {
            this.uuid = uuid
            this.username = postUser.username
            this.passwordHash = passwordHash
            this.role = UserRole.valueOf(postUser.role)
            this.company = CompanyEntity[UUID.fromString(postUser.companyUuid)]
        }

        return userRepository.insertUser(userEntity).toModel()
    }

    override suspend fun getToken(postAuth: PostAuth): String? {
        val user = userRepository.getUserByUsername(postAuth.username) ?: return null
        return if (verify(postAuth.password, user.passwordHash.toByteArray())) {
            jwtService.generateToken(user.uuid)
        } else {
            null
        }
    }
}