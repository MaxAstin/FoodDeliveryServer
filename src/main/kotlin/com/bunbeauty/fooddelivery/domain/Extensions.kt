package com.bunbeauty.fooddelivery.domain

import com.bunbeauty.fooddelivery.domain.model.ListWrapper
import java.util.*

fun <T : Any> List<T>.toListWrapper(): ListWrapper<T> {
    return ListWrapper(this.size, this)
}

fun String.toUuid(): UUID = UUID.fromString(this)

val mapUuid: String.() -> UUID = {
    UUID.fromString(this)
}