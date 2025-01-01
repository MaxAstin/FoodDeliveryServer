package com.bunbeauty.fooddelivery.network

import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.statement.HttpResponse

suspend inline fun <reified R> safeCall(
    networkCall: () -> HttpResponse
): ApiResult<R> {
    return try {
        ApiResult.Success<R>(networkCall().body()).also {
            println("ApiResult.Success")
        }
    } catch (exception: ClientRequestException) {
        println("ClientRequestException ${exception.message}")
        ApiResult.Error(exception)
    } catch (exception: Throwable) {
        println("Throwable ${exception.message}")
        ApiResult.Error(exception)
    }
}
