package com.bunbeauty.fooddelivery.data.repo.user

import com.bunbeauty.fooddelivery.domain.model.user.GetUser
import com.bunbeauty.fooddelivery.domain.model.user.InsertUser
import java.util.*

interface IUserRepository {

    suspend fun getCompanyUuidByUserUuid(uuid: UUID): String?
    suspend fun getUserByUuid(uuid: UUID): GetUser?
    suspend fun getUserByUsername(username: String): GetUser?
    suspend fun insertUser(insertUser: InsertUser): GetUser
}