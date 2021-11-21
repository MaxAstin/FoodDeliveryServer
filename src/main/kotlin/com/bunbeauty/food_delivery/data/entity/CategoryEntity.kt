package com.bunbeauty.food_delivery.data.entity

import com.bunbeauty.food_delivery.data.model.category.GetCategory
import com.bunbeauty.food_delivery.data.table.CategoryTable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID

data class CategoryEntity(
    val uuid: EntityID<String>,
) : Entity<String>(uuid) {
    val name: String by CategoryTable.name

    companion object : EntityClass<String, CategoryEntity>(CategoryTable)

    fun toCategory() = GetCategory(
        uuid = uuid.value,
        name = name
    )
}