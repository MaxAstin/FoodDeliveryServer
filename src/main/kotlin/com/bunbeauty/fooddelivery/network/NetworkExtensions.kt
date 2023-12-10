package com.bunbeauty.fooddelivery.network

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

inline fun <reified BODY> HttpRequestBuilder.buildRequest(
    path: String,
    parameters: Map<String, Any> = mapOf(),
    body: BODY? = null,
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
            buildRequest<Any>(
                path = path,
                parameters = parameters,
            )
        }
    }
}

suspend inline fun <reified BODY, reified R> HttpClient.postData(
    path: String,
    body: BODY,
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
    return (this as? ApiResult.Success)?.data
}