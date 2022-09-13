package com.bunbeauty.fooddelivery.service.client_user

import com.bunbeauty.fooddelivery.auth.IJwtService
import com.bunbeauty.fooddelivery.data.Constants.CLIENT_USER_LOGIN_SESSION_TIMEOUT
import com.bunbeauty.fooddelivery.data.ext.toUuid
import com.bunbeauty.fooddelivery.data.model.client_user.*
import com.bunbeauty.fooddelivery.data.model.client_user.login.*
import com.bunbeauty.fooddelivery.data.repo.client_user.IClientUserRepository
import com.google.firebase.auth.FirebaseAuth
import org.joda.time.DateTime
import java.util.*
import com.bunbeauty.fooddelivery.data.model.client_user.ClientAuthResponse

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
            ClientAuthResponse(token = token)
        } else {
            null
        }
    }

    override suspend fun sendCode(postClientCodeRequest: PostClientCodeRequest): GetClientUserLoginSessionUuid? {
        return if (isRequestForTestPhone(postClientCodeRequest)) {
            GetClientUserLoginSessionUuid(
                uuid = UUID.randomUUID().toString()
            )
        } else {
            sendCode(postClientCodeRequest.phoneNumber)?.let { code ->
                val insertClientUserLoginSession = InsertClientUserLoginSession(
                    phoneNumber = postClientCodeRequest.phoneNumber,
                    time = DateTime.now().millis,
                    code = code
                )
                clientUserRepository.insertClientUserLoginSession(insertClientUserLoginSession)
            }
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
            ClientAuthResponse(token = token)
        } else {
            null
        }
    }

    override suspend fun createTestClientUserPhone(postTestClientUserPhone: PostTestClientUserPhone): GetTestClientUserPhone {
        return clientUserRepository.insertTestClientUserPhone(
            InsertTestClientUserPhone(
                phoneNumber = postTestClientUserPhone.phoneNumber,
                code = postTestClientUserPhone.code
            )
        )
    }

    override suspend fun getTestClientUserPhoneList(): List<GetTestClientUserPhone> {
        return clientUserRepository.getTestClientUserPhoneList()
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

    suspend fun isRequestForTestPhone(postClientCodeRequest: PostClientCodeRequest): Boolean {
        return isForTestPhone(postClientCodeRequest.phoneNumber)
    }

    suspend fun sendCode(phoneNumber: String): String? {
        return ""
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