package com.bunbeauty.food_delivery.data.table

import org.jetbrains.exposed.dao.id.UUIDTable

object StreetTable : UUIDTable() {

    val name = varchar("name", 512)
    val cafe = reference("cafe", CafeTable)
    val isVisible = bool("isVisible")

}