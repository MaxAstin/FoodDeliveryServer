package com.bunbeauty.fooddelivery.data.repo

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.ClientUserLoginSessionEntity
import com.bunbeauty.fooddelivery.data.model.client_user.login.GetAuthSessionUuid
import com.bunbeauty.fooddelivery.data.model.client_user.login.InsertAuthSession

class AuthorizationRepository {

    suspend fun insertAuthSession(insertAuthSession: InsertAuthSession): GetAuthSessionUuid = query {
        ClientUserLoginSessionEntity.new {
            phoneNumber = insertAuthSession.phoneNumber
            time = insertAuthSession.time
            code = insertAuthSession.code
        }.toGetAuthSessionUuid()
    }
}