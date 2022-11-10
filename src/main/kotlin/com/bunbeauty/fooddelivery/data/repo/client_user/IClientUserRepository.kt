package com.bunbeauty.fooddelivery.data.repo.client_user

import com.bunbeauty.fooddelivery.data.model.client_user.GetClientUser
import com.bunbeauty.fooddelivery.data.model.client_user.InsertClientUser
import com.bunbeauty.fooddelivery.data.model.client_user.UpdateClientUser
import com.bunbeauty.fooddelivery.data.model.client_user.login.*
import java.util.*

interface IClientUserRepository {

    suspend fun insertClientUserLoginSession(insertClientUserLoginSession: InsertClientUserLoginSession): GetClientUserLoginSessionUuid
    suspend fun getClientUserLoginSessionByUuid(uuid: UUID): GetClientUserLoginSession?
    suspend fun insertTestClientUserPhone(insertTestClientUserPhone: InsertTestClientUserPhone): GetTestClientUserPhone
    suspend fun getTestClientUserPhoneByPhoneNumber(phoneNumber: String): GetTestClientUserPhone?
    suspend fun getTestClientUserPhoneList(): List<GetTestClientUserPhone>

    suspend fun getClientUserByUuid(uuid: UUID): GetClientUser?
    suspend fun getClientUserByPhoneNumberAndCompayUuid(phoneNumber: String, companyUuid: UUID): GetClientUser?
    suspend fun insertClientUser(insertClientUser: InsertClientUser): GetClientUser
    suspend fun updateClientUserByUuid(updateClientUser: UpdateClientUser): GetClientUser?

}