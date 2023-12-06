package com.bunbeauty.fooddelivery.data.features.auth

import com.bunbeauty.fooddelivery.domain.model.auth.BaseResponse
import com.bunbeauty.fooddelivery.network.ApiResult
import com.bunbeauty.fooddelivery.network.getData
import io.ktor.client.*

class AuthNetworkDataSource(private val client: HttpClient) {

    suspend fun sendSms(phoneNumber: String, sign: String, text: String): ApiResult<BaseResponse> {
        return client.getData(
            path = "sms/send",
            parameters = mapOf(
                "number" to phoneNumber,
                "sign" to sign,
                "text" to text,
            )
        )
    }

    suspend fun testSendSms(phoneNumber: String, sign: String, text: String): ApiResult<BaseResponse> {
        return client.getData(
            path = "sms/testsend",
            parameters = mapOf(
                "number" to phoneNumber,
                "sign" to sign,
                "text" to text,
            )
        )
    }

}