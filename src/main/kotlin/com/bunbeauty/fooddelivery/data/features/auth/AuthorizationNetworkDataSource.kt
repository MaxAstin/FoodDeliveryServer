package com.bunbeauty.fooddelivery.data.features.auth

import com.bunbeauty.fooddelivery.data.features.auth.remotemodel.BaseResponse
import com.bunbeauty.fooddelivery.network.ApiResult
import com.bunbeauty.fooddelivery.network.getData
import com.bunbeauty.fooddelivery.network.postData
import io.ktor.client.*
import kotlinx.serialization.Serializable

class AuthorizationNetworkDataSource(private val client: HttpClient) {

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

    suspend fun checkBalance(): ApiResult<BaseResponse> {
        return client.postData(
            path = "balance",
            body = BalanceRequest()
        )
    }

    @Serializable
    class BalanceRequest

}