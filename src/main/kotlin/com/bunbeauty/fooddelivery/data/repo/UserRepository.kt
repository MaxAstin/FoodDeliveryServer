package com.bunbeauty.fooddelivery.data.repo

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.CityEntity
import com.bunbeauty.fooddelivery.data.entity.UserEntity
import com.bunbeauty.fooddelivery.data.entity.company.CompanyEntity
import com.bunbeauty.fooddelivery.data.table.UserTable
import com.bunbeauty.fooddelivery.domain.model.user.GetUser
import com.bunbeauty.fooddelivery.domain.model.user.InsertUser
import java.util.*

class UserRepository {

    suspend fun getCompanyByUserUuid(uuid: UUID): CompanyEntity? = query {
        UserEntity.findById(uuid)?.city?.company
    }

    suspend fun getUserByUuid(uuid: UUID): GetUser? = query {
        UserEntity.findById(uuid)?.toUser()
    }

    suspend fun getUserByUsername(username: String): GetUser? = query {
        UserEntity.find {
            UserTable.username eq username
        }.firstOrNull()?.toUser()
    }

    suspend fun insertUser(insertUser: InsertUser): GetUser = query {
        UserEntity.new {
            username = insertUser.username
            passwordHash = insertUser.passwordHash
            role = insertUser.role
            city = CityEntity[insertUser.cityUuid]
        }.toUser()
    }
}