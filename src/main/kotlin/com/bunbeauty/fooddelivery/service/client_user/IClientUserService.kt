package com.bunbeauty.fooddelivery.service.client_user

import com.bunbeauty.fooddelivery.data.model.client_user.*

interface IClientUserService {

    suspend fun login(clientUserAuth: PostClientUserAuth): ClientAuthResponse?
    suspend fun sendCode(postClientCodeRequest: PostClientCodeRequest): GetClientUserLoginSession?
    suspend fun checkCode(clientUserAuth: PostClientUserAuth): ClientAuthResponse?
    suspend fun getClientUserByUuid(clientUserUuid: String): GetClientUser?
    suspend fun updateClientUserByUuid(clientUserUuid: String, patchClientUser: PatchClientUser): GetClientUser?
}