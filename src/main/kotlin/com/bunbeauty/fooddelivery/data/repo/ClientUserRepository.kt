package com.bunbeauty.fooddelivery.data.repo

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.ClientUserEntity
import com.bunbeauty.fooddelivery.data.entity.company.CompanyEntity
import com.bunbeauty.fooddelivery.data.features.clientuser.mapper.mapClientUserEntity
import com.bunbeauty.fooddelivery.data.features.clientuser.mapper.mapClientUserWithOrdersEntity
import com.bunbeauty.fooddelivery.data.table.ClientUserTable
import com.bunbeauty.fooddelivery.domain.feature.clientuser.model.ClientUser
import com.bunbeauty.fooddelivery.domain.feature.clientuser.model.ClientUserWithOrders
import com.bunbeauty.fooddelivery.domain.model.client_user.InsertClientUser
import com.bunbeauty.fooddelivery.domain.model.client_user.UpdateClientUser
import com.bunbeauty.fooddelivery.domain.toUuid
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import java.util.*

class ClientUserRepository {

    suspend fun getClientUserByPhoneNumberAndCompanyUuid(
        phoneNumber: String,
        companyUuid: UUID
    ): ClientUserWithOrders? = query {
        ClientUserEntity.find {
            (ClientUserTable.phoneNumber eq phoneNumber) and
                (ClientUserTable.company eq companyUuid) and
                (ClientUserTable.isActive eq true)
        }.singleOrNull()
            ?.mapClientUserWithOrdersEntity()
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

    suspend fun getCompanyByUuid(uuid: String): ClientUser? = query {
        (ClientUserTable).slice(ClientUserTable.columns)
            .select {
                ClientUserTable.id eq uuid.toUuid()
            }.singleOrNull()?.mapClientUserEntity()
    }

    suspend fun insertClientUser(insertClientUser: InsertClientUser): ClientUserWithOrders = query {
        ClientUserEntity.new {
            phoneNumber = insertClientUser.phoneNumber
            email = insertClientUser.email
            company = CompanyEntity[insertClientUser.companyUuid]
        }.mapClientUserWithOrdersEntity()
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
