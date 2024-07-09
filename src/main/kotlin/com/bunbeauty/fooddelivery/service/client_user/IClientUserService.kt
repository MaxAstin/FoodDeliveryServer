package com.bunbeauty.fooddelivery.service.client_user

import com.bunbeauty.fooddelivery.domain.model.client_user.*

interface IClientUserService {

    suspend fun login(clientUserAuth: PostClientUserAuth): ClientAuthResponse
    suspend fun getClientUserByUuid(clientUserUuid: String): GetClientUser?
    suspend fun getClientSettingsByUuid(clientUserUuid: String): GetClientSettings?
    suspend fun updateClientUserByUuid(clientUserUuid: String, patchClientUser: PatchClientUserSettings): GetClientUser?
    suspend fun updateClientUserSettingsByUuid(clientUserUuid: String, patchClientUser: PatchClientUserSettings): GetClientSettings?
}