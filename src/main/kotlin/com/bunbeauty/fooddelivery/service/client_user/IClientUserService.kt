package com.bunbeauty.fooddelivery.service.client_user

import com.bunbeauty.fooddelivery.data.model.Token
import com.bunbeauty.fooddelivery.data.model.client_user.GetClientUser
import com.bunbeauty.fooddelivery.data.model.client_user.PostClientUserAuth

interface IClientUserService {

    suspend fun getToken(clientUserAuth: PostClientUserAuth): Token?
    suspend fun getClientUserByUuid(clientUserUuid: String): GetClientUser?
}