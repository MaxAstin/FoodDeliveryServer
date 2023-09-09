package com.bunbeauty.fooddelivery.data.repo.client_user

import com.bunbeauty.fooddelivery.data.model.client_user.GetClientSettings
import com.bunbeauty.fooddelivery.data.model.client_user.GetClientUser
import com.bunbeauty.fooddelivery.data.model.client_user.InsertClientUser
import com.bunbeauty.fooddelivery.data.model.client_user.UpdateClientUser
import com.bunbeauty.fooddelivery.data.model.client_user.login.*
import java.util.UUID

interface IClientUserRepository {

    suspend fun insertClientUserLoginSession(insertClientUserLoginSession: InsertAuthSession): GetAuthSessionUuid
    suspend fun getClientUserLoginSessionByUuid(uuid: UUID): GetClientUserLoginSession?
    suspend fun getTestClientUserPhoneByPhoneNumber(phoneNumber: String): GetTestClientUserPhone?

    suspend fun getClientUserByUuid(uuid: UUID): GetClientUser?
    suspend fun getClientSettingsByUuid(uuid: UUID): GetClientSettings?
    suspend fun getClientUserByPhoneNumberAndCompayUuid(phoneNumber: String, companyUuid: UUID): GetClientUser?
    suspend fun insertClientUser(insertClientUser: InsertClientUser): GetClientUser
    suspend fun updateClientUserByUuid(updateClientUser: UpdateClientUser): GetClientUser?
    suspend fun updateClientUserSettingsByUuid(updateClientUser: UpdateClientUser): GetClientSettings?

}