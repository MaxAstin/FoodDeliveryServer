package com.bunbeauty.fooddelivery.service.client_user

import com.bunbeauty.fooddelivery.data.model.client_user.*
import com.bunbeauty.fooddelivery.data.model.client_user.login.*

interface IClientUserService {

    @Deprecated("Old version of login by Firebase")
    suspend fun login(clientUserAuth: PostClientUserAuth): ClientAuthResponse?
    suspend fun sendCode(postClientCodeRequest: PostClientCodeRequest): GetClientUserLoginSessionUuid?
    suspend fun checkCode(postClientCode: PostClientCode): ClientAuthResponse?
    suspend fun createTestClientUserPhone(postTestClientUserPhone: PostTestClientUserPhone): GetTestClientUserPhone
    suspend fun getClientUserByUuid(clientUserUuid: String): GetClientUser?
    suspend fun updateClientUserByUuid(clientUserUuid: String, patchClientUser: PatchClientUser): GetClientUser?
}