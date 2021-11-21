package com.bunbeauty.food_delivery.data.table

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

object CityTable : IdTable<String>() {

    val uuid = varchar("uuid", 512)
    val name = varchar("name", 512)
    val offset = integer("offset")
    val isVisible = bool("isVisible")

    override val id: Column<EntityID<String>> = uuid.entityId()
}
