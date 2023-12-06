package com.bunbeauty.fooddelivery.network

import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.statement.*

suspend inline fun <reified R> safeCall(
    networkCall: () -> HttpResponse
): ApiResult<R> {
    return try {
        ApiResult.Success(networkCall().body())
    } catch (exception: ClientRequestException) {
        ApiResult.Error(exception)
    } catch (exception: Throwable) {
        ApiResult.Error(exception)
    }
}