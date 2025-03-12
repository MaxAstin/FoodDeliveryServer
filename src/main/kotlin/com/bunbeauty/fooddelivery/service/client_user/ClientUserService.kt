package com.bunbeauty.fooddelivery.service.client_user

import com.bunbeauty.fooddelivery.auth.IJwtService
import com.bunbeauty.fooddelivery.data.repo.ClientUserRepository
import com.bunbeauty.fooddelivery.domain.error.orThrowNotFoundByUserUuidError
import com.bunbeauty.fooddelivery.domain.error.orThrowNotFoundByUuidError
import com.bunbeauty.fooddelivery.domain.feature.clientuser.mapper.mapClientUserToClientSettingsWithOrders
import com.bunbeauty.fooddelivery.domain.feature.clientuser.mapper.mapClientUserWithCafesToClientSettingsWithOrders
import com.bunbeauty.fooddelivery.domain.feature.clientuser.mapper.mapClientUserWithOrders
import com.bunbeauty.fooddelivery.domain.model.client_user.ClientAuthResponse
import com.bunbeauty.fooddelivery.domain.model.client_user.GetClientSettings
import com.bunbeauty.fooddelivery.domain.model.client_user.GetClientUser
import com.bunbeauty.fooddelivery.domain.model.client_user.InsertClientUser
import com.bunbeauty.fooddelivery.domain.model.client_user.PatchClientUserSettings
import com.bunbeauty.fooddelivery.domain.model.client_user.PostClientUserAuth
import com.bunbeauty.fooddelivery.domain.model.client_user.UpdateClientUser
import com.bunbeauty.fooddelivery.domain.toUuid
import com.google.firebase.auth.FirebaseAuth

class ClientUserService(
    private val firebaseAuth: FirebaseAuth,
    private val clientUserRepository: ClientUserRepository,
    private val jwtService: IJwtService
) : IClientUserService {

    override suspend fun login(clientUserAuth: PostClientUserAuth): ClientAuthResponse {
        val firebaseUser = firebaseAuth.getUser(clientUserAuth.firebaseUuid)
        if (firebaseUser.phoneNumber != clientUserAuth.phoneNumber) {
            credentialsError()
        }

        var clientUser = clientUserRepository.getClientUserByPhoneNumberAndCompanyUuid(
            clientUserAuth.phoneNumber,
            clientUserAuth.companyUuid.toUuid()
        )
        if (clientUser == null) {
            val insertClientUser = InsertClientUser(
                phoneNumber = clientUserAuth.phoneNumber,
                email = null,
                companyUuid = clientUserAuth.companyUuid.toUuid()
            )
            clientUser = clientUserRepository.insertClientUser(insertClientUser)
        }

        return ClientAuthResponse(
            token = jwtService.generateToken(clientUser),
            userUuid = clientUser.uuid
        )
    }

    override suspend fun getClientUserByUuid(clientUserUuid: String): GetClientUser {
        return clientUserRepository.getClientWithOrdersUserByUuid(uuid = clientUserUuid)
            .orThrowNotFoundByUuidError(uuid = clientUserUuid)
            .mapClientUserWithOrders()
    }

    override suspend fun getClientSettingsByUuid(clientUserUuid: String): GetClientSettings {
        return clientUserRepository.getCompanyByUuid(uuid = clientUserUuid)
            .orThrowNotFoundByUserUuidError(uuid = clientUserUuid)
            .mapClientUserToClientSettingsWithOrders()
    }

    override suspend fun updateClientUserByUuid(
        clientUserUuid: String,
        patchClientUser: PatchClientUserSettings
    ): GetClientUser {
        return clientUserRepository.updateClientUserByUuid(
            UpdateClientUser(
                uuid = clientUserUuid.toUuid(),
                email = patchClientUser.email,
                isActive = patchClientUser.isActive
            )
        ).orThrowNotFoundByUuidError(uuid = clientUserUuid)
            .mapClientUserWithOrders()
    }

    override suspend fun updateClientUserSettingsByUuid(
        clientUserUuid: String,
        patchClientUser: PatchClientUserSettings
    ): GetClientSettings {
        return clientUserRepository.updateClientUserByUuid(
            UpdateClientUser(
                uuid = clientUserUuid.toUuid(),
                email = patchClientUser.email,
                isActive = patchClientUser.isActive
            )
        ).orThrowNotFoundByUuidError(uuid = clientUserUuid)
            .mapClientUserWithCafesToClientSettingsWithOrders()
    }

    private fun credentialsError() {
        error("Unable to log in with provided credentials")
    }
}
