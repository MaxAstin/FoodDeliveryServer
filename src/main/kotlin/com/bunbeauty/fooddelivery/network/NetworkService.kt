package com.bunbeauty.fooddelivery.network

import com.bunbeauty.fooddelivery.data.model.auth.SendSmsResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class NetworkService(private val client: HttpClient) {

    suspend fun sendSms(phoneNumber: String, sign: String, text: String): ApiResult<SendSmsResponse> {
        return getData(
            path = "sms/send",
            parameters = mapOf(
                "number" to phoneNumber,
                "sign" to sign,
                "text" to text,
            )
        )
    }

    private suspend inline fun <reified R> getData(
        path: String,
        parameters: Map<String, Any> = mapOf(),
    ): ApiResult<R> {
        return safeCall {
            client.get {
                buildRequest(
                    path = path,
                    parameters = parameters,
                )
            }
        }
    }

    private suspend inline fun <reified R> safeCall(
        networkCall: () -> HttpResponse
    ): ApiResult<R> {
        return try {
            val call = networkCall()
            println("request.url = ${call.request.url}")
            println("attributes = ${call.request.attributes}")
            println("method = ${call.request.method}")
            println("headers = ${call.request.content.headers}")
            ApiResult.Success(call.body())
        } catch (exception: ClientRequestException) {
            ApiResult.Error(exception)
        } catch (exception: Throwable) {
            ApiResult.Error(exception)
        }
    }

    private fun HttpRequestBuilder.buildRequest(
        path: String,
        parameters: Map<String, Any> = mapOf(),
        body: Any? = null,
    ) {
        if (body != null) {
            setBody(body)
        }
        url {
            path(path)
        }
        parameters.forEach { parameterMap ->
            parameter(parameterMap.key, parameterMap.value)
        }
    }

}