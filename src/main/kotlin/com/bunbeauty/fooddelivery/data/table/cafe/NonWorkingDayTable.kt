package com.bunbeauty.fooddelivery.data.table.cafe

import org.jetbrains.exposed.dao.id.UUIDTable

object NonWorkingDayTable : UUIDTable() {

    val timestamp = long("timestamp")
    val isVisible = bool("isVisible").default(true)

    val cafe = reference("cafe", CafeTable)

}