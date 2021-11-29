package com.bunbeauty.food_delivery.data.repo.user

import com.bunbeauty.food_delivery.data.DatabaseFactory.query
import com.bunbeauty.food_delivery.data.entity.CompanyEntity
import com.bunbeauty.food_delivery.data.entity.UserEntity
import com.bunbeauty.food_delivery.data.model.user.GetUser
import com.bunbeauty.food_delivery.data.model.user.InsertUser
import com.bunbeauty.food_delivery.data.table.UserTable
import java.util.*

class UserRepository : IUserRepository {

    override suspend fun getCompanyUuidByUserUuid(uuid: UUID): String? = query {
        UserEntity.findById(uuid)?.company?.uuid
    }

    override suspend fun getUserByUuid(uuid: UUID): GetUser? = query {
        UserEntity.findById(uuid)?.toUser()
    }

    override suspend fun getUserByUsername(username: String): GetUser? = query {
        UserEntity.find {
            UserTable.username eq username
        }.firstOrNull()
            ?.toUser()
    }

    override suspend fun insertUser(insertUser: InsertUser): GetUser = query {
        UserEntity.new {
            username = insertUser.username
            passwordHash = insertUser.passwordHash
            company = CompanyEntity[insertUser.companyUuid]  //CompanyEntity[insertUser.companyUuid]
            role = insertUser.role
        }.toUser()
    }
}