package com.bunbeauty.fooddelivery.data.table.cafe

import org.jetbrains.exposed.dao.id.UUIDTable

object DeliveryZonePointTable : UUIDTable() {

    val order = integer("order")
    val latitude = double("latitude")
    val longitude = double("longitude")
    val isVisible = bool("isVisible")

    val zone = reference("zone", DeliveryZoneTable)
}
