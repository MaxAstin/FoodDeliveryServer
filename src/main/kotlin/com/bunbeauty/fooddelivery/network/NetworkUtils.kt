package com.bunbeauty.fooddelivery.network

import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.statement.*

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
