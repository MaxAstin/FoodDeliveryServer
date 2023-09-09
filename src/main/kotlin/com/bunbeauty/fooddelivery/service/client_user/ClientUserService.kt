package com.bunbeauty.fooddelivery.service.client_user

import com.bunbeauty.fooddelivery.auth.IJwtService
import com.bunbeauty.fooddelivery.data.Constants.CLIENT_USER_LOGIN_SESSION_TIMEOUT
import com.bunbeauty.fooddelivery.data.ext.toUuid
import com.bunbeauty.fooddelivery.data.model.client_user.*
import com.bunbeauty.fooddelivery.data.model.client_user.login.*
import com.bunbeauty.fooddelivery.data.repo.client_user.IClientUserRepository
import com.google.firebase.auth.FirebaseAuth
import org.joda.time.DateTime
import java.util.UUID

class ClientUserService(
    private val firebaseAuth: FirebaseAuth,
    private val clientUserRepository: IClientUserRepository,
    private val jwtService: IJwtService,
) : IClientUserService {

    override suspend fun login(clientUserAuth: PostClientUserAuth): ClientAuthResponse? {
        val firebaseUser = firebaseAuth.getUser(clientUserAuth.firebaseUuid)
        return if (firebaseUser.phoneNumber == clientUserAuth.phoneNumber) {
            var getClientUser = clientUserRepository.getClientUserByPhoneNumberAndCompayUuid(
                clientUserAuth.phoneNumber,
                clientUserAuth.companyUuid.toUuid()
            )
            if (getClientUser == null) {
                val insertClientUser = InsertClientUser(
                    phoneNumber = clientUserAuth.phoneNumber,
                    email = null,
                    companyUuid = clientUserAuth.companyUuid.toUuid(),
                )
                getClientUser = clientUserRepository.insertClientUser(insertClientUser)
            }

            val token = jwtService.generateToken(getClientUser)
            ClientAuthResponse(
                token = token,
                userUuid = getClientUser.uuid
            )
        } else {
            null
        }
    }

    override suspend fun checkCode(postClientCode: PostClientCode): ClientAuthResponse? {
        return if ((isCodeForTestPhone(postClientCode) && isCodeActualForTestPhone(postClientCode))
            || isCodeActual(postClientCode)
        ) {
            val getClientUser = clientUserRepository.getClientUserByPhoneNumberAndCompayUuid(
                postClientCode.phoneNumber,
                postClientCode.companyUuid.toUuid()
            ) ?: registerClientUser(postClientCode)
            val token = jwtService.generateToken(getClientUser)
            ClientAuthResponse(
                token = token,
                userUuid = getClientUser.uuid
            )
        } else {
            null
        }
    }

    override suspend fun getClientUserByUuid(clientUserUuid: String): GetClientUser? {
        return clientUserRepository.getClientUserByUuid(clientUserUuid.toUuid())
    }

    override suspend fun getClientSettingsByUuid(clientUserUuid: String): GetClientSettings? {
        return clientUserRepository.getClientSettingsByUuid(clientUserUuid.toUuid())
    }

    override suspend fun updateClientUserByUuid(
        clientUserUuid: String,
        patchClientUser: PatchClientUserSettings,
    ): GetClientUser? {
        return clientUserRepository.updateClientUserByUuid(
            UpdateClientUser(
                uuid = clientUserUuid.toUuid(),
                email = patchClientUser.email,
                isActive = patchClientUser.isActive,
            )
        )
    }

    override suspend fun updateClientUserSettingsByUuid(
        clientUserUuid: String,
        patchClientUser: PatchClientUserSettings
    ): GetClientSettings? {
        return clientUserRepository.updateClientUserSettingsByUuid(
            UpdateClientUser(
                uuid = clientUserUuid.toUuid(),
                email = patchClientUser.email,
                isActive = patchClientUser.isActive,
            )
        )
    }

    suspend fun isRequestForTestPhone(postClientCodeRequest: PostClientCodeRequest): Boolean {
        return isForTestPhone(postClientCodeRequest.phoneNumber)
    }

    suspend fun isCodeForTestPhone(postClientCode: PostClientCode): Boolean {
        return isForTestPhone(postClientCode.phoneNumber)
    }

    suspend fun isForTestPhone(phoneNumber: String): Boolean {
        return clientUserRepository.getTestClientUserPhoneByPhoneNumber(phoneNumber) != null
    }

    suspend fun isCodeActualForTestPhone(postClientCode: PostClientCode): Boolean {
        return clientUserRepository.getTestClientUserPhoneByPhoneNumber(postClientCode.phoneNumber)
            ?.let { getTestClientUserPhone ->
                getTestClientUserPhone.code == postClientCode.code
            } ?: false
    }

    suspend fun isCodeActual(postClientCode: PostClientCode): Boolean {
        return clientUserRepository.getClientUserLoginSessionByUuid(postClientCode.uuid.toUuid())
            .let { clientUserLoginSessionWithCode ->
                clientUserLoginSessionWithCode != null &&
                        clientUserLoginSessionWithCode.code == postClientCode.code &&
                        clientUserLoginSessionWithCode.phoneNumber == postClientCode.phoneNumber &&
                        isClientUserLoginSessionIsActual(clientUserLoginSessionWithCode.time)
            }
    }

    fun isClientUserLoginSessionIsActual(time: Long): Boolean {
        return time + CLIENT_USER_LOGIN_SESSION_TIMEOUT > DateTime.now().millis
    }

    suspend fun registerClientUser(postClientCode: PostClientCode): GetClientUser {
        val insertClientUser = InsertClientUser(
            phoneNumber = postClientCode.phoneNumber,
            email = null,
            companyUuid = postClientCode.companyUuid.toUuid(),
        )
        return clientUserRepository.insertClientUser(insertClientUser)
    }

}