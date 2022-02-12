package com.bunbeauty.fooddelivery.data.repo.client_user

import com.bunbeauty.fooddelivery.data.model.client_user.GetClientUser
import com.bunbeauty.fooddelivery.data.model.client_user.InsertClientUser
import com.bunbeauty.fooddelivery.data.model.client_user.login.GetClientUserLoginSessionUuid
import com.bunbeauty.fooddelivery.data.model.client_user.login.GetClientUserLoginSession
import com.bunbeauty.fooddelivery.data.model.client_user.login.InsertClientUserLoginSession
import java.util.*

interface IClientUserRepository {

    suspend fun insertClientUserLoginSession(insertClientUserLoginSession: InsertClientUserLoginSession): GetClientUserLoginSessionUuid
    suspend fun getClientUserLoginSessionByUuid(uuid: UUID): GetClientUserLoginSession?

    suspend fun getClientUserByUuid(uuid: UUID): GetClientUser?
    suspend fun getClientUserByPhoneNumber(phoneNumber: String): GetClientUser?
    suspend fun insertClientUser(insertClientUser: InsertClientUser): GetClientUser
    suspend fun updateClientUserByUuid(uuid: UUID, email: String?): GetClientUser?

}