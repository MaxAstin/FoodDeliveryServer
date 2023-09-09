package com.bunbeauty.fooddelivery.service

import com.bunbeauty.fooddelivery.data.ext.toUuid
import com.bunbeauty.fooddelivery.data.model.client_user.login.*
import com.bunbeauty.fooddelivery.data.model.request.RequestAvailability
import com.bunbeauty.fooddelivery.data.repo.AuthorizationRepository
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

class AuthorizationService(
    private val requestService: RequestService,
    private val authorizationRepository: AuthorizationRepository,
    private val companyRepository: CompanyRepository,
    private val networkService: NetworkService,
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
                if (!phoneNumberRegex.matches(postClientCodeRequest.phoneNumber)) {
                    error("Invalid phone number")
                }

                val testClientUserPhone = authorizationRepository.getTestNumber(postClientCodeRequest.phoneNumber)
                val phoneNumber =  testClientUserPhone?.phoneNumber ?: postClientCodeRequest.phoneNumber
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
                                code = code
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

    suspend fun createTestClientUserPhone(postTestClientUserPhone: PostTestClientUserPhone): GetTestClientUserPhone {
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

    private suspend fun getSmsText(otpCode: String, companyUuid: String): String {
        val company = companyRepository.getCompanyByUuid(companyUuid.toUuid()) ?: error("Company not found")
        return "$otpCode $MESSAGE_TEXT ${company.name}"
    }

}