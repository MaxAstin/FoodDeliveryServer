package com.bunbeauty.food_delivery.data.repo.client_user

import com.bunbeauty.food_delivery.data.model.client_user.GetClientUser
import com.bunbeauty.food_delivery.data.model.client_user.InsertClientUser
import com.google.type.PhoneNumber
import java.util.*

sealed interface IClientUserRepository {

    suspend fun getClientUserByUuid(uuid: UUID): GetClientUser?
    suspend fun getClientUserByPhoneNumber(phoneNumber: String): GetClientUser?
    suspend fun insertClientUser(insertClientUser: InsertClientUser): GetClientUser

}