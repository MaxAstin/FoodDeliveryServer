package com.bunbeauty.fooddelivery.data.repo

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.ClientAuthSessionEntity
import com.bunbeauty.fooddelivery.data.entity.TestClientUserPhoneEntity
import com.bunbeauty.fooddelivery.data.entity.company.CompanyEntity
import com.bunbeauty.fooddelivery.data.model.client_user.login.*
import com.bunbeauty.fooddelivery.data.table.TestClientUserPhoneTable
import java.util.*

class AuthorizationRepository {

    suspend fun insertAuthSession(insertAuthSession: InsertAuthSession): GetClientAuthSessionUuid = query {
        ClientAuthSessionEntity.new {
            phoneNumber = insertAuthSession.phoneNumber
            time = insertAuthSession.time
            attemptsLeft = insertAuthSession.attemptsLeft
            isConfirmed = insertAuthSession.isConfirmed
            company = CompanyEntity[insertAuthSession.companyUuid]
        }.toClientAuthSessionUuid()
    }

    suspend fun getAuthSessionByUuid(uuid: UUID): GetClientAuthSession? = query {
        ClientAuthSessionEntity.findById(uuid)?.toClientAuthSession()
    }

    suspend fun updateAuthSession(updateAuthSession: UpdateAuthSession): GetClientAuthSession? = query {
        ClientAuthSessionEntity.findById(updateAuthSession.uuid)?.apply {
            updateAuthSession.phoneNumber?.let { newPhoneNumber ->
                phoneNumber = newPhoneNumber
            }
            updateAuthSession.time?.let { newTime ->
                time = newTime
            }
            updateAuthSession.attemptsLeft?.let { newAttemptsLeft ->
                attemptsLeft = newAttemptsLeft
            }
            updateAuthSession.isConfirmed?.let { newIsConfirmed ->
                isConfirmed = newIsConfirmed
            }
        }?.toClientAuthSession()
    }

    suspend fun getTestClientUserPhone(phoneNumber: String): GetTestClientUserPhone? = query {
        TestClientUserPhoneEntity.find {
            TestClientUserPhoneTable.phoneNumber eq phoneNumber
        }.singleOrNull()?.toTestClientUserPhone()
    }

    suspend fun insertTestClientUserPhone(insertTestClientUserPhone: InsertTestClientUserPhone): GetTestClientUserPhone =
        query {
            TestClientUserPhoneEntity.new {
                phoneNumber = insertTestClientUserPhone.phoneNumber
                code = insertTestClientUserPhone.code
            }.toTestClientUserPhone()
        }

    suspend fun getTestClientUserPhoneList(): List<GetTestClientUserPhone> = query {
        TestClientUserPhoneEntity.all().map { testClientUserPhoneEntity ->
            testClientUserPhoneEntity.toTestClientUserPhone()
        }
    }
}