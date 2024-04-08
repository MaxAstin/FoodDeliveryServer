package com.bunbeauty.fooddelivery.domain.error

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

fun notFoundByUserUuidError(entity: KClass<*>, userUuid: String): Nothing {
    error("${entity.simpleName} with userUuid = $userUuid was not found")
}

fun parameterIsRequiredError(parameterName: String): Nothing {
    error("Parameter $parameterName is required")
}

fun noAccessToCompanyError(companyUuid: String): Nothing {
    error("User doesn't has access to this company - $companyUuid")
}

fun isNotAvailableForYourCompanyError(entity: KClass<*>, uuid: String, companyUuid: String): Nothing {
    error("${entity.simpleName}($uuid) is not available for your Company($companyUuid)")
}