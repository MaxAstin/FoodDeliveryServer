package com.bunbeauty.fooddelivery.data.repo.client_user

import com.bunbeauty.fooddelivery.data.model.client_user.GetClientSettings
import com.bunbeauty.fooddelivery.data.model.client_user.GetClientUser
import com.bunbeauty.fooddelivery.data.model.client_user.InsertClientUser
import com.bunbeauty.fooddelivery.data.model.client_user.UpdateClientUser
import java.util.*

interface IClientUserRepository {

    suspend fun getClientUserByUuid(uuid: UUID): GetClientUser?
    suspend fun getClientSettingsByUuid(uuid: UUID): GetClientSettings?
    suspend fun getClientUserByPhoneNumberAndCompayUuid(phoneNumber: String, companyUuid: UUID): GetClientUser?
    suspend fun insertClientUser(insertClientUser: InsertClientUser): GetClientUser
    suspend fun updateClientUserByUuid(updateClientUser: UpdateClientUser): GetClientUser?
    suspend fun updateClientUserSettingsByUuid(updateClientUser: UpdateClientUser): GetClientSettings?

}