package com.bunbeauty.fooddelivery.domain.error

inline fun <reified T> T?.orThrowNotFoundByUuidError(uuid: String): T {
    return this ?: notFoundByUuidError(T::class, uuid)
}