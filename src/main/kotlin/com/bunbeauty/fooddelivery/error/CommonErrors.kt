package com.bunbeauty.fooddelivery.error

import com.bunbeauty.fooddelivery.network.ApiResult
import kotlin.reflect.KClass

fun errorWithCode(message: String, code: Int): Nothing = throw ExceptionWithCode(message = message, code = code)

fun somethingWentWrongError(apiResult: ApiResult.Error<*>): Nothing {
    somethingWentWrongError(apiResult.throwable)
}

fun somethingWentWrongError(throwable: Throwable): Nothing {
    error("Something went wrong: ${throwable.message}")
}

fun notFoundByUuidError(entity: KClass<*>, uuid: String): Nothing {
    error("${entity.simpleName} with uuid = $uuid was not found")
}