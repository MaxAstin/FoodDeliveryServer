package com.bunbeauty.fooddelivery.data.table

import com.bunbeauty.fooddelivery.data.table.cafe.CafeTable
import org.jetbrains.exposed.dao.id.UUIDTable

object StreetTable : UUIDTable() {

    val name = varchar("name", 512)
    val latitude = double("latitude").default(0.0)
    val longitude = double("longitude").default(0.0)
    val isVisible = bool("isVisible")

    val cafe = reference("cafe", CafeTable)
}
