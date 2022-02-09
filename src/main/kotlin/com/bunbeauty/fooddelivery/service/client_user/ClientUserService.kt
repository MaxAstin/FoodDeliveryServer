package com.bunbeauty.fooddelivery.service.client_user

import com.bunbeauty.fooddelivery.auth.IJwtService
import com.bunbeauty.fooddelivery.data.ext.toUuid
import com.bunbeauty.fooddelivery.data.model.client_user.*
import com.bunbeauty.fooddelivery.data.repo.client_user.IClientUserRepository
import com.google.firebase.auth.FirebaseAuth


class ClientUserService(
    private val firebaseAuth: FirebaseAuth,
    private val clientUserRepository: IClientUserRepository,
    private val jwtService: IJwtService,
) : IClientUserService {

    override suspend fun login(clientUserAuth: PostClientUserAuth): ClientAuthResponse? {
        val firebaseUser = firebaseAuth.getUser(clientUserAuth.firebaseUuid)
        return if (firebaseUser.phoneNumber == clientUserAuth.phoneNumber) {
            var getClientUser = clientUserRepository.getClientUserByPhoneNumber(clientUserAuth.phoneNumber)
            if (getClientUser == null) {
                val insertClientUser = InsertClientUser(
                    phoneNumber = clientUserAuth.phoneNumber,
                    email = null,
                    companyUuid = clientUserAuth.companyUuid.toUuid(),
                )
                getClientUser = clientUserRepository.insertClientUser(insertClientUser)
            }

            val token = jwtService.generateToken(getClientUser)
            ClientAuthResponse(token = token)
        } else {
            null
        }
    }

    override suspend fun sendCode(postClientCodeRequest: PostClientCodeRequest): GetClientUserLoginSession? {
        return null
    }

    override suspend fun checkCode(clientUserAuth: PostClientUserAuth): ClientAuthResponse? {
        return null
    }

    override suspend fun getClientUserByUuid(clientUserUuid: String): GetClientUser? {
        return clientUserRepository.getClientUserByUuid(clientUserUuid.toUuid())
    }

    override suspend fun updateClientUserByUuid(
        clientUserUuid: String,
        patchClientUser: PatchClientUser
    ): GetClientUser? {
        return clientUserRepository.updateClientUserByUuid(clientUserUuid.toUuid(), patchClientUser.email)
    }

}