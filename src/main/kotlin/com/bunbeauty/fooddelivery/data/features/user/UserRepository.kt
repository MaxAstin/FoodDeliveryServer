package com.bunbeauty.fooddelivery.data.features.user

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.CityEntity
import com.bunbeauty.fooddelivery.data.entity.UserEntity
import com.bunbeauty.fooddelivery.data.features.company.mapper.mapCompanyEntity
import com.bunbeauty.fooddelivery.data.table.UserTable
import com.bunbeauty.fooddelivery.domain.feature.company.Company
import com.bunbeauty.fooddelivery.domain.feature.user.User
import com.bunbeauty.fooddelivery.domain.model.user.InsertUser
import java.util.*

class UserRepository {

    suspend fun getCompanyByUserUuid(uuid: UUID): Company? = query {
        UserEntity.findById(uuid)?.city?.company?.mapCompanyEntity()
    }

    suspend fun getUserByUuid(uuid: UUID): User? = query {
        UserEntity.findById(uuid)?.mapUserEntity()
    }

    suspend fun getUserByUsername(username: String): User? = query {
        UserEntity.find {
            UserTable.username eq username
        }.firstOrNull()?.mapUserEntity()
    }

    suspend fun insertUser(insertUser: InsertUser): User = query {
        UserEntity.new {
            username = insertUser.username
            passwordHash = insertUser.passwordHash
            role = insertUser.role
            city = CityEntity[insertUser.cityUuid]
        }.mapUserEntity()
    }
}