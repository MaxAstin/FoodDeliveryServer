package com.bunbeauty.food_delivery.data.entity

import com.bunbeauty.food_delivery.data.model.category.GetCategory
import com.bunbeauty.food_delivery.data.table.CategoryTable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class CategoryEntity(uuid: EntityID<UUID>) : UUIDEntity(uuid) {

    val uuid: String = uuid.value.toString()
    var name: String by CategoryTable.name

    companion object : UUIDEntityClass<CategoryEntity>(CategoryTable)

    fun toCategory() = GetCategory(
        uuid = uuid,
        name = name
    )
}