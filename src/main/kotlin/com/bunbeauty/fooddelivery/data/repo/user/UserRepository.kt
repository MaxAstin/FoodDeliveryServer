package com.bunbeauty.fooddelivery.data.repo.user

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.CityEntity
import com.bunbeauty.fooddelivery.data.entity.UserEntity
import com.bunbeauty.fooddelivery.data.model.user.GetUser
import com.bunbeauty.fooddelivery.data.model.user.InsertUser
import com.bunbeauty.fooddelivery.data.table.UserTable
import java.util.UUID

class UserRepository : IUserRepository {

    override suspend fun getCompanyUuidByUserUuid(uuid: UUID): String? = query {
        UserEntity.findById(uuid)?.city?.company?.uuid
    }

    override suspend fun getUserByUuid(uuid: UUID): GetUser? = query {
        UserEntity.findById(uuid)?.toUser()
    }

    override suspend fun getUserByUsername(username: String): GetUser? = query {
        UserEntity.find {
            UserTable.username eq username
        }.firstOrNull()?.toUser()
    }

    override suspend fun insertUser(insertUser: InsertUser): GetUser = query {
        UserEntity.new {
            username = insertUser.username
            passwordHash = insertUser.passwordHash
            role = insertUser.role
            city = CityEntity[insertUser.cityUuid]
        }.toUser()
    }
}