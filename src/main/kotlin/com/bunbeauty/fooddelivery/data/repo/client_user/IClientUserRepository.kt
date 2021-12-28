package com.bunbeauty.fooddelivery.data.repo.client_user

import com.bunbeauty.fooddelivery.data.model.client_user.GetClientUser
import com.bunbeauty.fooddelivery.data.model.client_user.InsertClientUser
import java.util.*

sealed interface IClientUserRepository {

    suspend fun getClientUserByUuid(uuid: UUID): GetClientUser?
    suspend fun getClientUserByPhoneNumber(phoneNumber: String): GetClientUser?
    suspend fun insertClientUser(insertClientUser: InsertClientUser): GetClientUser
    suspend fun updateClientUserByUuid(uuid: UUID, email: String?): GetClientUser?

}