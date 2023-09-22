package com.bunbeauty.fooddelivery.service

import com.bunbeauty.fooddelivery.auth.IJwtService
import com.bunbeauty.fooddelivery.data.ext.toUuid
import com.bunbeauty.fooddelivery.data.model.client_user.ClientAuthResponse
import com.bunbeauty.fooddelivery.data.model.client_user.GetClientUser
import com.bunbeauty.fooddelivery.data.model.client_user.InsertClientUser
import com.bunbeauty.fooddelivery.data.model.client_user.login.*
import com.bunbeauty.fooddelivery.data.model.request.RequestAvailability
import com.bunbeauty.fooddelivery.data.repo.AuthorizationRepository
import com.bunbeauty.fooddelivery.data.repo.ClientUserRepository
import com.bunbeauty.fooddelivery.data.repo.CompanyRepository
import com.bunbeauty.fooddelivery.network.ApiResult
import com.bunbeauty.fooddelivery.network.NetworkService
import com.bunbeauty.fooddelivery.service.ip.RequestService
import dev.turingcomplete.kotlinonetimepassword.HmacAlgorithm
import dev.turingcomplete.kotlinonetimepassword.HmacOneTimePasswordConfig
import dev.turingcomplete.kotlinonetimepassword.HmacOneTimePasswordGenerator
import org.joda.time.DateTime

private const val CODE_LENGTH = 6
private const val SEND_CODE_OPERATION_NAME = "sendCode"
private const val AUTH_SECRET_KEY = "AUTH_SECRET_KEY"
private const val DEFAULT_SIGN = "SMS Aero"
private const val MESSAGE_TEXT = "ваш код подтверждения для приложения"
private const val AUTH_SESSION_TIMEOUT = 5 * 60 * 1_000L // 5 min

class AuthorizationService(
    private val requestService: RequestService,
    private val authorizationRepository: AuthorizationRepository,
    private val companyRepository: CompanyRepository,
    private val networkService: NetworkService,
    private val clientUserRepository: ClientUserRepository,
    private val jwtService: IJwtService,
) {

    private val otpGenerator by lazy {
        val secret = System.getenv(AUTH_SECRET_KEY).toByteArray()
        val config = HmacOneTimePasswordConfig(codeDigits = CODE_LENGTH, hmacAlgorithm = HmacAlgorithm.SHA1)
        HmacOneTimePasswordGenerator(secret, config)
    }

    private val phoneNumberRegex by lazy {
        Regex("^[+]?7[0-9]{10}$")
    }

    suspend fun sendCode(
        companyUuid: String,
        postClientCodeRequest: PostClientCodeRequest,
        clientIp: String,
    ): GetAuthSessionUuid {
        when (val availability = requestService.isRequestAvailable(clientIp, SEND_CODE_OPERATION_NAME)) {
            RequestAvailability.Available -> {
                validatePhoneNumber(postClientCodeRequest.phoneNumber)

                val testClientUserPhone = authorizationRepository.getTestClientUserPhone(postClientCodeRequest.phoneNumber)
                val phoneNumber = testClientUserPhone?.phoneNumber ?: postClientCodeRequest.phoneNumber
                val currentMillis = DateTime.now().millis
                val code = testClientUserPhone?.code ?: otpGenerator.generate(currentMillis)
                val apiResult = if (testClientUserPhone == null) {
                    networkService.sendSms(
                        phoneNumber = phoneNumber,
                        sign = DEFAULT_SIGN,
                        text = getSmsText(code, companyUuid),
                    )
                } else {
                    networkService.testSendSms(
                        phoneNumber = phoneNumber,
                        sign = DEFAULT_SIGN,
                        text = getSmsText(code, companyUuid),
                    )
                }

                when (apiResult) {
                    is ApiResult.Success -> {
                        if (apiResult.data.success) {
                            val insertAuthSession = InsertAuthSession(
                                phoneNumber = phoneNumber,
                                time = currentMillis,
                                isConfirmed = false,
                                companyUuid = companyUuid.toUuid(),
                            )
                            return authorizationRepository.insertAuthSession(insertAuthSession)
                        } else {
                            error("Auth service error: ${apiResult.data.message}")
                        }
                    }

                    is ApiResult.Error -> error("Something went wrong: ${apiResult.throwable.message}")
                }
            }

            is RequestAvailability.NotAvailable -> error("Too many requests. Please wait ${availability.seconds} s")
        }
    }

    suspend fun checkCode(uuid: String, putClientCode: PutClientCode): ClientAuthResponse {
        val authSession = authorizationRepository.getAuthSessionByUuid(uuid.toUuid())
            ?: error("AuthSession with id = $uuid was not found")

        val testClientUserPhone = authorizationRepository.getTestClientUserPhone(authSession.phoneNumber)
        val isCodeValid = if (testClientUserPhone == null) {
            otpGenerator.isValid(putClientCode.code, authSession.time)
        } else {
            putClientCode.code == testClientUserPhone.code
        }
        if (!isCodeValid) {
            error("Invalid code")
        }

        val currentMillis = DateTime.now().millis
        if (currentMillis - authSession.time > AUTH_SESSION_TIMEOUT) {
            error("Auth session timout")
        }

        if (authSession.isConfirmed) {
            error("Phone number is already confirmed")
        }

        authorizationRepository.updateAuthSession(
            UpdateAuthSession(
                uuid = authSession.uuid.toUuid(),
                phoneNumber = authSession.phoneNumber,
                time = authSession.time,
                isConfirmed = true,
            )
        )

        val clientUser = clientUserRepository.getClientUserByPhoneNumberAndCompanyUuid(
            authSession.phoneNumber,
            authSession.companyUuid.toUuid(),
        ) ?: registerClientUser(authSession)
        val token = jwtService.generateToken(clientUser)

        return ClientAuthResponse(
            token = token,
            userUuid = clientUser.uuid
        )
    }

    suspend fun createTestClientUserPhone(postTestClientUserPhone: PostTestClientUserPhone): GetTestClientUserPhone {
        validatePhoneNumber(postTestClientUserPhone.phoneNumber)
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

    private suspend fun registerClientUser(authSession: GetAuthSession): GetClientUser {
        val insertClientUser = InsertClientUser(
            phoneNumber = authSession.phoneNumber,
            email = null,
            companyUuid = authSession.companyUuid.toUuid(),
        )
        return clientUserRepository.insertClientUser(insertClientUser)
    }

    private fun validatePhoneNumber(phoneNumber: String) {
        if (!phoneNumberRegex.matches(phoneNumber)) {
            error("Invalid phone number")
        }
    }

    private suspend fun getSmsText(otpCode: String, companyUuid: String): String {
        val company = companyRepository.getCompanyByUuid(companyUuid.toUuid()) ?: error("Company not found")
        return "$otpCode $MESSAGE_TEXT ${company.name}"
    }

}