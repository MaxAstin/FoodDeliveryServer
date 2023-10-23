package com.bunbeauty.fooddelivery.error

inline fun <reified T> T?.orThrowNotFoundByUuidError(uuid: String): T {
    return this ?: notFoundByUuidError(T::class, uuid)
}