package com.bunbeauty.fooddelivery.domain.error

inline fun <reified T> T?.orThrowNotFoundByUuidError(uuid: String): T {
    return this ?: notFoundByUuidError(T::class, uuid)
}

inline fun <reified T> T?.orThrowNotFoundByUserUuidError(uuid: String): T {
    return this ?: notFoundByUserUuidError(T::class, uuid)
}