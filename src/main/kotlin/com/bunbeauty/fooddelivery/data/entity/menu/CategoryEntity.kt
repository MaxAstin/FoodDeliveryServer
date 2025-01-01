package com.bunbeauty.fooddelivery.data.entity.menu

import com.bunbeauty.fooddelivery.data.entity.company.CompanyEntity
import com.bunbeauty.fooddelivery.data.table.menu.CategoryTable
import com.bunbeauty.fooddelivery.domain.feature.menu.model.category.GetCategory
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