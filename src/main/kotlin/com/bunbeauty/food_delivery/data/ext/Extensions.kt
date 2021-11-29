package com.bunbeauty.food_delivery.data.ext

import com.bunbeauty.food_delivery.data.model.ListWrapper
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.sql.SizedCollection
import java.util.*

fun <T : Any> List<T>.toListWrapper(): ListWrapper<T> {
    return ListWrapper(this.size, this)
}

fun String.toUuid(): UUID = UUID.fromString(this)