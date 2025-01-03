package com.bunbeauty.fooddelivery.network

sealed class ApiResult<T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error<T>(val throwable: Throwable) : ApiResult<T>()
}
