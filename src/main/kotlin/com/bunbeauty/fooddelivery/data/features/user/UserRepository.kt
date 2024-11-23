package com.bunbeauty.fooddelivery.data.features.user

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.CityEntity
import com.bunbeauty.fooddelivery.data.entity.UserEntity
import com.bunbeauty.fooddelivery.data.features.company.mapper.mapCompanyEntity
import com.bunbeauty.fooddelivery.data.features.user.mapper.toUser
import com.bunbeauty.fooddelivery.data.table.UserTable
import com.bunbeauty.fooddelivery.domain.feature.company.Company
import com.bunbeauty.fooddelivery.domain.feature.user.model.domain.NotificationData
import com.bunbeauty.fooddelivery.domain.feature.user.model.domain.User
import com.bunbeauty.fooddelivery.domain.model.user.InsertUser
import java.util.*

class UserRepository {

    suspend fun getCompanyByUserUuid(uuid: UUID): Company? = query {
        UserEntity.findById(uuid)?.city?.company?.mapCompanyEntity()
    }

    suspend fun getUserByUuid(uuid: UUID): User? = query {
        UserEntity.findById(uuid)?.toUser()
    }

    suspend fun getUserListByCityUuid(cityUuid: UUID): List<User> = query {
        UserEntity.find {
            UserTable.city eq cityUuid
        }.map(UserEntity::toUser)
    }

    suspend fun getUserByUsername(username: String): User? = query {
        UserEntity.find {
            UserTable.username eq username
        }.firstOrNull()?.toUser()
    }

    suspend fun insertUser(insertUser: InsertUser): User = query {
        UserEntity.new {
            username = insertUser.username
            passwordHash = insertUser.passwordHash
            role = insertUser.role
            city = CityEntity[insertUser.cityUuid]
        }.toUser()
    }

    suspend fun updateNotificationToken(
        uuid: UUID,
        notificationData: NotificationData
    ): User? {
        return query {
            UserEntity.findById(id = uuid)
                ?.apply {
                    notificationToken = notificationData.token
                    notificationDevice = notificationData.device
                    updateNotificationTokenDateTime = notificationData.updateTokenDateTime
                }?.toUser()
        }
    }

    suspend fun clearNotificationToken(
        uuid: UUID,
        dateTime: String
    ): User? {
        return query {
            UserEntity.findById(id = uuid)
                ?.apply {
                    notificationToken = null
                    updateNotificationTokenDateTime = dateTime
                }?.toUser()
        }
    }

    suspend fun updateUnlimitedNotification(
        uuid: UUID,
        isEnabled: Boolean
    ): User? {
        return query {
            UserEntity.findById(id = uuid)
                ?.apply {
                    unlimitedNotification = isEnabled
                }?.toUser()
        }
    }
}