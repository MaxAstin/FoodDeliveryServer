package com.bunbeauty.food_delivery.data.repo.user

import com.bunbeauty.food_delivery.data.DatabaseFactory.query
import com.bunbeauty.food_delivery.data.entity.UserEntity
import com.bunbeauty.food_delivery.data.table.UserTable

class UserRepository : IUserRepository {

    override suspend fun getUserByUuid(uuid: String): UserEntity? = query {
        UserEntity.findById(uuid)
    }

    override suspend fun getUserByUsername(username: String): UserEntity? = query {
        UserEntity.find {
            UserTable.username eq username
        }.firstOrNull()
    }

    override suspend fun insertUser(userEntity: UserEntity): UserEntity = query {
        UserEntity.new {
            uuid = userEntity.uuid
            username = userEntity.username
            passwordHash = userEntity.passwordHash
            company = userEntity.company
            role = userEntity.role
        }
    }
}