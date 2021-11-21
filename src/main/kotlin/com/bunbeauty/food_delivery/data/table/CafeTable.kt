package com.bunbeauty.food_delivery.data.table

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

object CafeTable : IdTable<String>() {

    val uuid = varchar("uuid", 512)
    val fromTime = integer("fromTime")
    val toTime = integer("toTime")
    val phone = varchar("phone", 512)
    val latitude = double("latitude")
    val longitude = double("longitude")
    val address = varchar("address", 512)
    val city = reference("city", CityTable)
    val isVisible = bool("isVisible")

    override val id: Column<EntityID<String>> = uuid.entityId()
}