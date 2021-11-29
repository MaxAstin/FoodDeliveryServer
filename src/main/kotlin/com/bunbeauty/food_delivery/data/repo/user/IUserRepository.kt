package com.bunbeauty.food_delivery.data.repo.user

import com.bunbeauty.food_delivery.data.model.user.GetUser
import com.bunbeauty.food_delivery.data.model.user.InsertUser
import java.util.*

interface IUserRepository {


    suspend fun getCompanyUuidByUserUuid(uuid: UUID): String?
    suspend fun getUserByUuid(uuid: UUID): GetUser?
    suspend fun getUserByUsername(username: String): GetUser?
    suspend fun insertUser(insertUser: InsertUser): GetUser
}