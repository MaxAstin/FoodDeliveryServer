package com.bunbeauty.fooddelivery.data.entity

import com.bunbeauty.fooddelivery.data.entity.company.CompanyEntity
import com.bunbeauty.fooddelivery.data.model.category.GetCategory
import com.bunbeauty.fooddelivery.data.table.CategoryTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class CategoryEntity(uuid: EntityID<UUID>) : UUIDEntity(uuid) {

    val uuid: String = uuid.value.toString()
    var name: String by CategoryTable.name
    var priority: Int by CategoryTable.priority
    var company: CompanyEntity by CompanyEntity referencedOn CategoryTable.company

    companion object : UUIDEntityClass<CategoryEntity>(CategoryTable)

    fun toCategory() = GetCategory(
        uuid = uuid,
        name = name,
        priority = priority,
    )
}