package com.bunbeauty.food_delivery.service.client_user

import com.bunbeauty.food_delivery.data.model.client_user.GetClientUser
import com.bunbeauty.food_delivery.data.model.client_user.PostClientUserAuth

interface IClientUserService {

    suspend fun getToken(clientUserAuth: PostClientUserAuth): String?
    suspend fun getClientUserByUuid(clientUserUuid: String): GetClientUser?
}