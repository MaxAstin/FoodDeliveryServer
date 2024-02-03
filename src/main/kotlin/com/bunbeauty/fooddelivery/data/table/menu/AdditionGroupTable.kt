package com.bunbeauty.fooddelivery.data.table.menu

import org.jetbrains.exposed.dao.id.UUIDTable

object AdditionGroupTable : UUIDTable() {

    val name = varchar("name", 512)
    val singleChoice = bool("singleChoice")
    val priority = integer("priority")
    val isVisible = bool("isVisible")

}