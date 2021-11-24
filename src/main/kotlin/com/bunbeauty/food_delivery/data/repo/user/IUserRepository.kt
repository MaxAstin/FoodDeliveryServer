package com.bunbeauty.food_delivery.data.repo.user

import com.bunbeauty.food_delivery.data.entity.UserEntity

interface IUserRepository {

    suspend fun getUserByUuid(uuid: String): UserEntity?
    suspend fun getUserByUsername(username: String): UserEntity?
    suspend fun insertUser(userEntity: UserEntity): UserEntity
}