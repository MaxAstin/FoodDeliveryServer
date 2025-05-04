package com.bunbeauty.fooddelivery.data.repo

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.ClientUserEntity
import com.bunbeauty.fooddelivery.data.entity.company.CompanyEntity
import com.bunbeauty.fooddelivery.data.features.clientuser.mapper.mapClientUserEntity
import com.bunbeauty.fooddelivery.data.features.clientuser.mapper.mapClientUserResultRow
import com.bunbeauty.fooddelivery.data.features.clientuser.mapper.mapClientUserWithOrdersEntity
import com.bunbeauty.fooddelivery.data.table.ClientUserTable
import com.bunbeauty.fooddelivery.domain.feature.clientuser.model.ClientUser
import com.bunbeauty.fooddelivery.domain.feature.clientuser.model.ClientUserWithOrders
import com.bunbeauty.fooddelivery.domain.feature.user.model.domain.NotificationData
import com.bunbeauty.fooddelivery.domain.model.client_user.InsertClientUser
import com.bunbeauty.fooddelivery.domain.model.client_user.UpdateClientUser
import com.bunbeauty.fooddelivery.domain.toUuid
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update
import java.util.*

class ClientUserRepository {

    suspend fun getClientUserByPhoneNumberAndCompanyUuid(
        phoneNumber: String,
        companyUuid: UUID
    ): ClientUser? = query {
        (ClientUserTable).slice(ClientUserTable.columns)
            .select {
                (ClientUserTable.phoneNumber eq phoneNumber) and
                    (ClientUserTable.company eq companyUuid) and
                    (ClientUserTable.isActive eq true)
            }.singleOrNull()?.mapClientUserResultRow()
    }

    suspend fun getClientWithOrdersUserByUuid(uuid: String): ClientUserWithOrders? = query {
        ClientUserEntity.findById(uuid.toUuid())?.let { clientUserEntity ->
            if (clientUserEntity.isActive) {
                clientUserEntity.mapClientUserWithOrdersEntity()
            } else {
                null
            }
        }
    }

    suspend fun updateNotificationToken(
        uuid: UUID,
        notificationData: NotificationData
    ) = query {
        ClientUserTable.update({ ClientUserTable.id eq uuid }) { clientUser ->
            clientUser[notificationToken] = notificationData.token
            clientUser[notificationDevice] = notificationData.device
            clientUser[updateNotificationTokenDateTime] = notificationData.updateTokenDateTime
        }
    }

    suspend fun getClientByUuid(uuid: String): ClientUser? = query {
        (ClientUserTable).slice(ClientUserTable.columns)
            .select {
                ClientUserTable.id eq uuid.toUuid()
            }.singleOrNull()?.mapClientUserResultRow()
    }

    suspend fun insertClientUser(insertClientUser: InsertClientUser): ClientUser = query {
        ClientUserEntity.new {
            phoneNumber = insertClientUser.phoneNumber
            email = insertClientUser.email
            company = CompanyEntity[insertClientUser.companyUuid]
        }.mapClientUserEntity()
    }

    suspend fun updateClientUserByUuid(updateClientUser: UpdateClientUser): ClientUserWithOrders? = query {
        ClientUserEntity.findById(updateClientUser.uuid)?.let { clientUserEntity ->
            if (clientUserEntity.isActive) {
                clientUserEntity.apply {
                    updateClientUser.email?.let { newEmail ->
                        email = newEmail
                    }
                    updateClientUser.isActive?.let { newIsActive ->
                        isActive = newIsActive
                    }
                }.mapClientUserWithOrdersEntity()
            } else {
                null
            }
        }
    }
}
