package com.bunbeauty.fooddelivery.data.table.cafe

import com.bunbeauty.fooddelivery.data.table.CityTable
import org.jetbrains.exposed.dao.id.UUIDTable

object CafeTable : UUIDTable() {

    val fromTime = integer("fromTime")
    val toTime = integer("toTime")
    val offset = integer("offset").default(0)
    val phoneNumber = varchar("phone", 512)
    val latitude = double("latitude")
    val longitude = double("longitude")
    val address = varchar("address", 512)
    val codeCounter = integer("codeCounter").default(0)
    val city = reference("city", CityTable)
    val isVisible = bool("isVisible")

}