package com.bunbeauty.fooddelivery.data.repo

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.ClientUserEntity
import com.bunbeauty.fooddelivery.data.entity.company.CompanyEntity
import com.bunbeauty.fooddelivery.data.features.clientuser.mapper.mapClientUserEntity
import com.bunbeauty.fooddelivery.data.table.ClientUserTable
import com.bunbeauty.fooddelivery.domain.feature.clientuser.model.ClientUser
import com.bunbeauty.fooddelivery.domain.model.client_user.InsertClientUser
import com.bunbeauty.fooddelivery.domain.model.client_user.UpdateClientUser
import com.bunbeauty.fooddelivery.domain.toUuid
import org.jetbrains.exposed.sql.and
import java.util.*

class ClientUserRepository {

    suspend fun getClientUserByPhoneNumberAndCompanyUuid(
        phoneNumber: String,
        companyUuid: UUID,
    ): ClientUser? = query {
        ClientUserEntity.find {
            (ClientUserTable.phoneNumber eq phoneNumber) and
                    (ClientUserTable.company eq companyUuid) and
                    (ClientUserTable.isActive eq true)
        }.singleOrNull()
            ?.mapClientUserEntity()
    }

    suspend fun getClientUserByUuid(uuid: String): ClientUser? = query {
        ClientUserEntity.findById(uuid.toUuid())?.let { clientUserEntity ->
            if (clientUserEntity.isActive) {
                clientUserEntity.mapClientUserEntity()
            } else {
                null
            }
        }
    }

    suspend fun insertClientUser(insertClientUser: InsertClientUser): ClientUser = query {
        ClientUserEntity.new {
            phoneNumber = insertClientUser.phoneNumber
            email = insertClientUser.email
            company = CompanyEntity[insertClientUser.companyUuid]
        }.mapClientUserEntity()
    }

    suspend fun updateClientUserByUuid(updateClientUser: UpdateClientUser): ClientUser? = query {
        ClientUserEntity.findById(updateClientUser.uuid)?.let { clientUserEntity ->
            if (clientUserEntity.isActive) {
                clientUserEntity.apply {
                    updateClientUser.email?.let { newEmail ->
                        email = newEmail
                    }
                    updateClientUser.isActive?.let { newIsActive ->
                        isActive = newIsActive
                    }
                }.mapClientUserEntity()
            } else {
                null
            }
        }
    }
}