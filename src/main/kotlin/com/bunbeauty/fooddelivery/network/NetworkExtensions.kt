package com.bunbeauty.fooddelivery.network

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

fun HttpRequestBuilder.buildRequest(
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

suspend inline fun <reified R> HttpClient.getData(
    path: String,
    parameters: Map<String, Any> = mapOf(),
): ApiResult<R> {
    return safeCall {
        get {
            buildRequest(
                path = path,
                parameters = parameters,
            )
        }
    }
}

suspend inline fun <reified R> HttpClient.postData(
    path: String,
    body: Any,
    parameters: Map<String, Any> = mapOf(),
): ApiResult<R> {
    return safeCall {
        post {
            buildRequest(
                path = path,
                parameters = parameters,
                body = body,
            )
        }
    }
}

fun <T> ApiResult<T>.getDataOrNull(): T? {
    if (this is ApiResult.Error) {
        println(throwable.message)
    }
    return (this as? ApiResult.Success)?.data
}