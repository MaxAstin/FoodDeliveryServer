package com.bunbeauty.fooddelivery.data.ext

import com.bunbeauty.fooddelivery.data.model.ListWrapper
import java.util.*

fun <T : Any> List<T>.toListWrapper(): ListWrapper<T> {
    return ListWrapper(this.size, this)
}

fun String.toUuid(): UUID = UUID.fromString(this)