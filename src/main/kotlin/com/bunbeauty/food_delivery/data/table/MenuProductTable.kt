package com.bunbeauty.food_delivery.data.table

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

object MenuProductTable: IdTable<String>() {

    val uuid = varchar("uuid", 512)
    val name = varchar("name", 512)
    val newPrice = integer("newPrice")
    val oldPrice = integer("oldPrice").nullable()
    val utils = varchar("utils", 512).nullable()
    val nutrition = integer("nutrition").nullable()
    val description = varchar("description", 512)
    val comboDescription = varchar("comboDescription", 512).nullable()
    val photoLink = varchar("photoLink", 512)
    val barcode = integer("barcode")
    val isVisible = bool("isVisible")
    //val categories = integer("categories")

    override val id: Column<EntityID<String>> = uuid.entityId()
}