package com.bunbeauty.fooddelivery.error

import com.bunbeauty.fooddelivery.network.ApiResult
import kotlin.reflect.KClass

fun somethingWentWrongError(apiResult: ApiResult.Error<*>): Nothing {
    somethingWentWrongError(apiResult.throwable)
}

fun somethingWentWrongError(throwable: Throwable): Nothing {
    error("Something went wrong: ${throwable.message}")
}

fun tooManyRequestsError(seconds: Int? = null): Nothing {
    var message = "Too many requests. Please wait"
    if (seconds != null) {
        message += " $seconds s"
    }
    error(message)
}

fun notFoundByUuidError(entity: KClass<*>, uuid: String): Nothing {
    error("${entity.simpleName} with uuid = $uuid was not found")
}