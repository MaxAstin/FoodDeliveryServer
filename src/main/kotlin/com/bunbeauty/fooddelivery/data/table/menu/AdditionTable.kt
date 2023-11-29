package com.bunbeauty.fooddelivery.data.table.menu

import org.jetbrains.exposed.dao.id.UUIDTable

object AdditionTable : UUIDTable() {

    val name = varchar("name", 512)
    val isSelected = bool("isSelected")
    val price = integer("price").nullable()
    val photoLink = varchar("photoLink", 512)
    val isVisible = bool("isVisible")

    val group = reference("group", AdditionGroupTable)

}