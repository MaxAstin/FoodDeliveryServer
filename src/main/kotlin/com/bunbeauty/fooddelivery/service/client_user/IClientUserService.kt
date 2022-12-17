package com.bunbeauty.fooddelivery.service.client_user

import com.bunbeauty.fooddelivery.data.model.client_user.*
import com.bunbeauty.fooddelivery.data.model.client_user.login.*

interface IClientUserService {

    suspend fun login(clientUserAuth: PostClientUserAuth): ClientAuthResponse?
    suspend fun sendCode(postClientCodeRequest: PostClientCodeRequest): GetClientUserLoginSessionUuid?
    suspend fun checkCode(postClientCode: PostClientCode): ClientAuthResponse?
    suspend fun createTestClientUserPhone(postTestClientUserPhone: PostTestClientUserPhone): GetTestClientUserPhone
    suspend fun getTestClientUserPhoneList(): List<GetTestClientUserPhone>
    suspend fun getClientUserByUuid(clientUserUuid: String): GetClientUser?
    suspend fun getClientSettingsByUuid(clientUserUuid: String): GetClientSettings?
    suspend fun updateClientUserByUuid(clientUserUuid: String, patchClientUser: PatchClientUserSettings): GetClientUser?
    suspend fun updateClientUserSettingsByUuid(clientUserUuid: String, patchClientUser: PatchClientUserSettings): GetClientSettings?
}