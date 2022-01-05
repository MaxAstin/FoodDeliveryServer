package com.bunbeauty.fooddelivery.service.client_user

import com.bunbeauty.fooddelivery.data.model.client_user.ClientAuthResponse
import com.bunbeauty.fooddelivery.data.model.client_user.GetClientUser
import com.bunbeauty.fooddelivery.data.model.client_user.PatchClientUser
import com.bunbeauty.fooddelivery.data.model.client_user.PostClientUserAuth

interface IClientUserService {

    suspend fun login(clientUserAuth: PostClientUserAuth): ClientAuthResponse?
    suspend fun getClientUserByUuid(clientUserUuid: String): GetClientUser?
    suspend fun updateClientUserByUuid(clientUserUuid: String, patchClientUser: PatchClientUser): GetClientUser?
}