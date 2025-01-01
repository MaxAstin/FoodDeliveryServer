package com.bunbeauty.fooddelivery.service

import com.bunbeauty.fooddelivery.auth.IJwtService
import com.bunbeauty.fooddelivery.data.features.auth.AuthorizationNetworkDataSource
import com.bunbeauty.fooddelivery.data.features.auth.AuthorizationRepository
import com.bunbeauty.fooddelivery.data.repo.ClientUserRepository
import com.bunbeauty.fooddelivery.data.repo.CompanyRepository
import com.bunbeauty.fooddelivery.domain.error.errorWithCode
import com.bunbeauty.fooddelivery.domain.error.orThrowNotFoundByUuidError
import com.bunbeauty.fooddelivery.domain.error.somethingWentWrongError
import com.bunbeauty.fooddelivery.domain.feature.clientuser.model.ClientUser
import com.bunbeauty.fooddelivery.domain.model.client_user.ClientAuthResponse
import com.bunbeauty.fooddelivery.domain.model.client_user.InsertClientUser
import com.bunbeauty.fooddelivery.domain.model.client_user.login.GetClientAuthSession
import com.bunbeauty.fooddelivery.domain.model.client_user.login.GetClientAuthSessionUuid
import com.bunbeauty.fooddelivery.domain.model.client_user.login.GetTestClientUserPhone
import com.bunbeauty.fooddelivery.domain.model.client_user.login.InsertAuthSession
import com.bunbeauty.fooddelivery.domain.model.client_user.login.InsertTestClientUserPhone
import com.bunbeauty.fooddelivery.domain.model.client_user.login.PostClientCodeRequest
import com.bunbeauty.fooddelivery.domain.model.client_user.login.PostTestClientUserPhone
import com.bunbeauty.fooddelivery.domain.model.client_user.login.PutClientCode
import com.bunbeauty.fooddelivery.domain.model.client_user.login.UpdateAuthSession
import com.bunbeauty.fooddelivery.domain.toUuid
import com.bunbeauty.fooddelivery.network.ApiResult
import com.bunbeauty.fooddelivery.service.ip.RequestService
import dev.turingcomplete.kotlinonetimepassword.HmacAlgorithm
import dev.turingcomplete.kotlinonetimepassword.HmacOneTimePasswordConfig
import dev.turingcomplete.kotlinonetimepassword.HmacOneTimePasswordGenerator
import org.joda.time.DateTime

private const val CODE_LENGTH = 6
private const val SEND_CODE_OPERATION_NAME = "sendCode"
private const val AUTH_SECRET_KEY = "AUTH_SECRET_KEY"
private const val DEFAULT_SIGN = "BunBeauty"
private const val MESSAGE_TEXT = "ваш код подтверждения для приложения"
private const val AUTH_SESSION_TIMEOUT = 5 * 60 * 1_000L // 5 min
private const val INITIAL_ATTEMPTS_COUNT = 3

private const val NO_ATTEMPTS_LEFT_CODE = 801
private const val INVALID_CODE = 802
private const val AUTH_SESSION_TIMEOUT_CODE = 803

class AuthorizationService(
    private val requestService: RequestService,
    private val authorizationRepository: AuthorizationRepository,
    private val companyRepository: CompanyRepository,
    private val authorizationNetworkDataSource: AuthorizationNetworkDataSource,
    private val clientUserRepository: ClientUserRepository,
    private val jwtService: IJwtService
) {

    private val otpGenerator by lazy {
        val secret = System.getenv(AUTH_SECRET_KEY).toByteArray()
        val config = HmacOneTimePasswordConfig(codeDigits = CODE_LENGTH, hmacAlgorithm = HmacAlgorithm.SHA1)
        HmacOneTimePasswordGenerator(secret, config)
    }

    private val phoneNumberRegex by lazy {
        Regex("^\\+7[0-9]{10}$")
    }

    suspend fun sendCode(
        companyUuid: String,
        postClientCodeRequest: PostClientCodeRequest,
        clientIp: String
    ): GetClientAuthSessionUuid {
        requestService.checkRequestAvailability(clientIp, SEND_CODE_OPERATION_NAME)

        validatePhoneNumber(postClientCodeRequest.phoneNumber)

        val testClientUserPhone = authorizationRepository.getTestClientUserPhone(postClientCodeRequest.phoneNumber)
        val phoneNumber = testClientUserPhone?.phoneNumber ?: postClientCodeRequest.phoneNumber
        val currentMillis = DateTime.now().millis
        val code = testClientUserPhone?.code ?: otpGenerator.generate(currentMillis)
        val apiResult = if (testClientUserPhone == null) {
            authorizationNetworkDataSource.sendSms(
                phoneNumber = phoneNumber,
                sign = DEFAULT_SIGN,
                text = getSmsText(code, companyUuid)
            )
        } else {
            authorizationNetworkDataSource.testSendSms(
                phoneNumber = phoneNumber,
                sign = DEFAULT_SIGN,
                text = getSmsText(code, companyUuid)
            )
        }

        when (apiResult) {
            is ApiResult.Success -> {
                if (apiResult.data.success) {
                    val insertAuthSession = InsertAuthSession(
                        phoneNumber = phoneNumber,
                        time = currentMillis,
                        attemptsLeft = INITIAL_ATTEMPTS_COUNT,
                        isConfirmed = false,
                        companyUuid = companyUuid.toUuid()
                    )
                    return authorizationRepository.insertAuthSession(insertAuthSession)
                } else {
                    authServiceError(apiResult.data.message)
                }
            }

            is ApiResult.Error -> somethingWentWrongError(apiResult.throwable)
        }
    }

    suspend fun checkCode(uuid: String, putClientCode: PutClientCode): ClientAuthResponse {
        val authSession = authorizationRepository.getAuthSessionByUuid(uuid.toUuid())
            .orThrowNotFoundByUuidError(uuid)

        if (authSession.attemptsLeft == 0) {
            noAttemptsError()
        }

        val testClientUserPhone = authorizationRepository.getTestClientUserPhone(authSession.phoneNumber)
        val isCodeValid = if (testClientUserPhone == null) {
            otpGenerator.isValid(putClientCode.code, authSession.time)
        } else {
            putClientCode.code == testClientUserPhone.code
        }
        if (!isCodeValid) {
            val updateAuthSession = UpdateAuthSession(
                uuid = authSession.uuid.toUuid(),
                attemptsLeft = authSession.attemptsLeft - 1
            )
            authorizationRepository.updateAuthSession(updateAuthSession)
            invalidCodeError()
        }

        val currentMillis = DateTime.now().millis
        if (currentMillis - authSession.time > AUTH_SESSION_TIMEOUT) {
            authSessionTimoutError()
        }

        if (authSession.isConfirmed) {
            alreadyConfirmedError()
        }

        authorizationRepository.updateAuthSession(
            UpdateAuthSession(
                uuid = authSession.uuid.toUuid(),
                isConfirmed = true
            )
        )

        val clientUser = clientUserRepository.getClientUserByPhoneNumberAndCompanyUuid(
            authSession.phoneNumber,
            authSession.companyUuid.toUuid()
        ) ?: registerClientUser(authSession)

        return ClientAuthResponse(
            token = jwtService.generateToken(clientUser),
            userUuid = clientUser.uuid
        )
    }

    suspend fun resendCode(uuid: String, clientIp: String) {
        requestService.checkRequestAvailability(clientIp, SEND_CODE_OPERATION_NAME)

        val authSession = authorizationRepository.getAuthSessionByUuid(uuid.toUuid())
            .orThrowNotFoundByUuidError(uuid)

        if (authSession.isConfirmed) {
            alreadyConfirmedError()
        }

        val testClientUserPhone = authorizationRepository.getTestClientUserPhone(authSession.phoneNumber)
        val phoneNumber = testClientUserPhone?.phoneNumber ?: authSession.phoneNumber
        val currentMillis = DateTime.now().millis
        val code = testClientUserPhone?.code ?: otpGenerator.generate(currentMillis)
        val apiResult = if (testClientUserPhone == null) {
            authorizationNetworkDataSource.sendSms(
                phoneNumber = phoneNumber,
                sign = DEFAULT_SIGN,
                text = getSmsText(code, authSession.companyUuid)
            )
        } else {
            authorizationNetworkDataSource.testSendSms(
                phoneNumber = phoneNumber,
                sign = DEFAULT_SIGN,
                text = getSmsText(code, authSession.companyUuid)
            )
        }

        when (apiResult) {
            is ApiResult.Success -> {
                if (apiResult.data.success) {
                    val updateAuthSession = UpdateAuthSession(
                        uuid = authSession.uuid.toUuid(),
                        attemptsLeft = INITIAL_ATTEMPTS_COUNT,
                        time = currentMillis
                    )
                    authorizationRepository.updateAuthSession(updateAuthSession)
                } else {
                    authServiceError(apiResult.data.message)
                }
            }

            is ApiResult.Error -> somethingWentWrongError(apiResult)
        }
    }

    suspend fun createTestClientUserPhone(postTestClientUserPhone: PostTestClientUserPhone): GetTestClientUserPhone {
        validatePhoneNumber(postTestClientUserPhone.phoneNumber)

        if (postTestClientUserPhone.code.length != CODE_LENGTH) {
            codeLengthError()
        }

        return authorizationRepository.insertTestClientUserPhone(
            InsertTestClientUserPhone(
                phoneNumber = postTestClientUserPhone.phoneNumber,
                code = postTestClientUserPhone.code
            )
        )
    }

    suspend fun getTestClientUserPhoneList(): List<GetTestClientUserPhone> {
        return authorizationRepository.getTestClientUserPhoneList()
    }

    private suspend fun registerClientUser(authSession: GetClientAuthSession): ClientUser {
        val insertClientUser = InsertClientUser(
            phoneNumber = authSession.phoneNumber,
            email = null,
            companyUuid = authSession.companyUuid.toUuid()
        )
        return clientUserRepository.insertClientUser(insertClientUser)
    }

    private fun validatePhoneNumber(phoneNumber: String) {
        if (!phoneNumberRegex.matches(phoneNumber)) {
            invalidPhoneNumberError()
        }
    }

    private suspend fun getSmsText(otpCode: String, companyUuid: String): String {
        val company = companyRepository.getCompanyByUuid(uuid = companyUuid.toUuid())
            .orThrowNotFoundByUuidError(uuid = companyUuid)
        return "$otpCode $MESSAGE_TEXT ${company.name}"
    }

    private fun invalidPhoneNumberError(): Nothing {
        error("Invalid phone number")
    }

    private fun authServiceError(message: String?): Nothing {
        error("Auth service error: $message")
    }

    private fun noAttemptsError(): Nothing {
        errorWithCode(
            message = "There are no attempts left",
            code = NO_ATTEMPTS_LEFT_CODE
        )
    }

    private fun invalidCodeError(): Nothing {
        errorWithCode(
            message = "Invalid code",
            code = INVALID_CODE
        )
    }

    private fun authSessionTimoutError(): Nothing {
        errorWithCode(
            message = "Auth session timout",
            code = AUTH_SESSION_TIMEOUT_CODE
        )
    }

    private fun alreadyConfirmedError(): Nothing {
        error("Phone number is already confirmed")
    }

    private fun codeLengthError(): Nothing {
        error("Code length must be $CODE_LENGTH")
    }
}
