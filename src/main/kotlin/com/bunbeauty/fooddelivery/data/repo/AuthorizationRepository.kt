package com.bunbeauty.fooddelivery.data.repo

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.ClientUserLoginSessionEntity
import com.bunbeauty.fooddelivery.data.entity.TestClientUserPhoneEntity
import com.bunbeauty.fooddelivery.data.model.client_user.login.GetAuthSessionUuid
import com.bunbeauty.fooddelivery.data.model.client_user.login.GetTestClientUserPhone
import com.bunbeauty.fooddelivery.data.model.client_user.login.InsertAuthSession
import com.bunbeauty.fooddelivery.data.model.client_user.login.InsertTestClientUserPhone
import com.bunbeauty.fooddelivery.data.table.TestClientUserPhoneTable

class AuthorizationRepository {

    suspend fun getTestNumber(phoneNumber: String): GetTestClientUserPhone? = query {
        TestClientUserPhoneEntity.find {
            TestClientUserPhoneTable.phoneNumber eq phoneNumber
        }.singleOrNull()?.toTestClientUserPhone()
    }

    suspend fun insertAuthSession(insertAuthSession: InsertAuthSession): GetAuthSessionUuid = query {
        ClientUserLoginSessionEntity.new {
            phoneNumber = insertAuthSession.phoneNumber
            time = insertAuthSession.time
            code = insertAuthSession.code
        }.toGetAuthSessionUuid()
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