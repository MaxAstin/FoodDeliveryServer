package com.bunbeauty.fooddelivery.service.client_user

import com.bunbeauty.fooddelivery.auth.IJwtService
import com.bunbeauty.fooddelivery.data.Constants.CLIENT_USER_LOGIN_SESSION_TIMEOUT
import com.bunbeauty.fooddelivery.data.ext.toUuid
import com.bunbeauty.fooddelivery.data.model.client_user.*
import com.bunbeauty.fooddelivery.data.model.client_user.login.GetClientUserLoginSessionUuid
import com.bunbeauty.fooddelivery.data.model.client_user.login.InsertClientUserLoginSession
import com.bunbeauty.fooddelivery.data.model.client_user.login.PostClientCode
import com.bunbeauty.fooddelivery.data.model.client_user.login.PostClientCodeRequest
import com.bunbeauty.fooddelivery.data.repo.client_user.IClientUserRepository
import com.google.firebase.auth.FirebaseAuth
import org.joda.time.DateTime

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

    override suspend fun sendCode(postClientCodeRequest: PostClientCodeRequest): GetClientUserLoginSessionUuid? {
        val code = sendCode(postClientCodeRequest.phoneNumber) ?: return null
        val insertClientUserLoginSession = InsertClientUserLoginSession(
            phoneNumber = postClientCodeRequest.phoneNumber,
            time = DateTime.now().millis,
            code = code
        )
        return clientUserRepository.insertClientUserLoginSession(insertClientUserLoginSession)
    }

    override suspend fun checkCode(postClientCode: PostClientCode): ClientAuthResponse? {
        return if (isCodeActual(postClientCode)) {
            val getClientUser = clientUserRepository.getClientUserByPhoneNumber(postClientCode.phoneNumber)
                ?: registerClientUser(postClientCode)
            val token = jwtService.generateToken(getClientUser)
            ClientAuthResponse(token = token)
        } else {
            null
        }
    }

    override suspend fun getClientUserByUuid(clientUserUuid: String): GetClientUser? {
        return clientUserRepository.getClientUserByUuid(clientUserUuid.toUuid())
    }

    override suspend fun updateClientUserByUuid(
        clientUserUuid: String,
        patchClientUser: PatchClientUser,
    ): GetClientUser? {
        return clientUserRepository.updateClientUserByUuid(clientUserUuid.toUuid(), patchClientUser.email)
    }

    suspend fun sendCode(phoneNumber: String): String? {
        return ""
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