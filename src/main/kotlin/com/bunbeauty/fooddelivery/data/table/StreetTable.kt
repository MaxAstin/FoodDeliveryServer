package com.bunbeauty.fooddelivery.data.table

import com.bunbeauty.fooddelivery.data.table.cafe.CafeTable
import org.jetbrains.exposed.dao.id.UUIDTable

object StreetTable : UUIDTable() {

    val name = varchar("name", 512)
    val cafe = reference("cafe", CafeTable)
    val isVisible = bool("isVisible")

}