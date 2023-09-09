package com.bunbeauty.fooddelivery.data.repo.client_user

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.*
import com.bunbeauty.fooddelivery.data.entity.company.CompanyEntity
import com.bunbeauty.fooddelivery.data.model.client_user.GetClientSettings
import com.bunbeauty.fooddelivery.data.model.client_user.GetClientUser
import com.bunbeauty.fooddelivery.data.model.client_user.InsertClientUser
import com.bunbeauty.fooddelivery.data.model.client_user.UpdateClientUser
import com.bunbeauty.fooddelivery.data.model.client_user.login.*
import com.bunbeauty.fooddelivery.data.table.ClientUserTable
import com.bunbeauty.fooddelivery.data.table.TestClientUserPhoneTable
import org.jetbrains.exposed.sql.and
import java.util.UUID

class ClientUserRepository : IClientUserRepository {

    override suspend fun insertClientUserLoginSession(insertClientUserLoginSession: InsertAuthSession): GetAuthSessionUuid =
        query {
            ClientUserLoginSessionEntity.new {
                phoneNumber = insertClientUserLoginSession.phoneNumber
                time = insertClientUserLoginSession.time
                code = insertClientUserLoginSession.code
            }.toGetAuthSessionUuid()
        }

    override suspend fun getClientUserLoginSessionByUuid(uuid: UUID): GetClientUserLoginSession? = query {
        ClientUserLoginSessionEntity.findById(uuid)?.toClientUserLoginSessionWithCode()
    }

    override suspend fun getTestClientUserPhoneByPhoneNumber(phoneNumber: String): GetTestClientUserPhone? = query {
        TestClientUserPhoneEntity.find {
            TestClientUserPhoneTable.phoneNumber eq phoneNumber
        }.firstOrNull()?.toTestClientUserPhone()
    }

    override suspend fun getClientUserByPhoneNumberAndCompayUuid(
        phoneNumber: String,
        companyUuid: UUID,
    ): GetClientUser? = query {
        ClientUserEntity.find {
            (ClientUserTable.phoneNumber eq phoneNumber) and
                    (ClientUserTable.company eq companyUuid) and
                    (ClientUserTable.isActive eq true)
        }.singleOrNull()?.toClientUser()
    }

    override suspend fun getClientUserByUuid(uuid: UUID): GetClientUser? = query {
        ClientUserEntity.findById(uuid)?.let { clientUserEntity ->
            if (clientUserEntity.isActive) {
                clientUserEntity.toClientUser()
            } else {
                null
            }
        }
    }

    override suspend fun getClientSettingsByUuid(uuid: UUID): GetClientSettings? = query {
        ClientSettingsEntity.findById(uuid)?.toClientSetting()
    }

    override suspend fun insertClientUser(insertClientUser: InsertClientUser): GetClientUser = query {
        ClientUserEntity.new {
            phoneNumber = insertClientUser.phoneNumber
            email = insertClientUser.email
            company = CompanyEntity[insertClientUser.companyUuid]
        }.toClientUser()
    }

    override suspend fun updateClientUserByUuid(updateClientUser: UpdateClientUser): GetClientUser? = query {
        ClientUserEntity.findById(updateClientUser.uuid)?.let { clientUserEntity ->
            if (clientUserEntity.isActive) {
                clientUserEntity.apply {
                    updateClientUser.email?.let { newEmail ->
                        email = newEmail
                    }
                    updateClientUser.isActive?.let { newIsActive ->
                        isActive = newIsActive
                    }
                }.toClientUser()
            } else {
                null
            }
        }
    }

    override suspend fun updateClientUserSettingsByUuid(updateClientUser: UpdateClientUser): GetClientSettings? = query {
        ClientSettingsEntity.findById(updateClientUser.uuid)?.let { clientSettingsEntity ->
            if (clientSettingsEntity.isActive) {
                clientSettingsEntity.apply {
                    updateClientUser.email?.let { newEmail ->
                        email = newEmail
                    }
                    updateClientUser.isActive?.let { newIsActive ->
                        isActive = newIsActive
                    }
                }.toClientSetting()
            } else {
                null
            }
        }
    }
}