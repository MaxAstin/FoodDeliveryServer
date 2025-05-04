package com.bunbeauty.fooddelivery.service.client_user

import com.bunbeauty.fooddelivery.domain.feature.user.model.api.PutNotificationToken
import com.bunbeauty.fooddelivery.domain.model.client_user.ClientAuthResponse
import com.bunbeauty.fooddelivery.domain.model.client_user.GetClientSettings
import com.bunbeauty.fooddelivery.domain.model.client_user.GetClientUser
import com.bunbeauty.fooddelivery.domain.model.client_user.PatchClientUserSettings
import com.bunbeauty.fooddelivery.domain.model.client_user.PostClientUserAuth

interface IClientUserService {

    suspend fun login(clientUserAuth: PostClientUserAuth): ClientAuthResponse
    suspend fun getClientUserByUuid(clientUserUuid: String): GetClientUser
    suspend fun getClientSettingsByUuid(clientUserUuid: String): GetClientSettings
    suspend fun updateClientUserByUuid(clientUserUuid: String, patchClientUser: PatchClientUserSettings): GetClientUser
    suspend fun updateClientUserSettingsByUuid(
        clientUserUuid: String,
        patchClientUser: PatchClientUserSettings
    ): GetClientSettings

    suspend fun updateNotificationToken(
        userUuid: String,
        putNotificationToken: PutNotificationToken
    )
}
