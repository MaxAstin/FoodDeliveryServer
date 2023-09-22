package com.bunbeauty.fooddelivery.data.repo

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.ClientUserLoginSessionEntity
import com.bunbeauty.fooddelivery.data.entity.TestClientUserPhoneEntity
import com.bunbeauty.fooddelivery.data.entity.company.CompanyEntity
import com.bunbeauty.fooddelivery.data.model.client_user.login.*
import com.bunbeauty.fooddelivery.data.table.TestClientUserPhoneTable
import java.util.*

class AuthorizationRepository {

    suspend fun insertAuthSession(insertAuthSession: InsertAuthSession): GetAuthSessionUuid = query {
        ClientUserLoginSessionEntity.new {
            phoneNumber = insertAuthSession.phoneNumber
            time = insertAuthSession.time
            isConfirmed = insertAuthSession.isConfirmed
            company = CompanyEntity[insertAuthSession.companyUuid]
        }.toAuthSessionUuid()
    }

    suspend fun getAuthSessionByUuid(uuid: UUID): GetAuthSession? = query {
        ClientUserLoginSessionEntity.findById(uuid)?.toAuthSession()
    }

    suspend fun updateAuthSession(updateAuthSession: UpdateAuthSession): GetAuthSession? = query {
        ClientUserLoginSessionEntity.findById(updateAuthSession.uuid)?.apply {
            phoneNumber = updateAuthSession.phoneNumber
            time = updateAuthSession.time
            isConfirmed = updateAuthSession.isConfirmed
        }?.toAuthSession()
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