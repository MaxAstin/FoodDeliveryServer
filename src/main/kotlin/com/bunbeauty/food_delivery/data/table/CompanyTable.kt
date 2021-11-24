package com.bunbeauty.food_delivery.data.table

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import java.util.*

object CompanyTable: IdTable<UUID>() {

    override val id: Column<EntityID<UUID>> = uuid("uuid").autoGenerate().entityId()

    val name = varchar("name", 512)

}