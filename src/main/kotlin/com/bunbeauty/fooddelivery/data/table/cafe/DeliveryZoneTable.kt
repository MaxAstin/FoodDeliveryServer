package com.bunbeauty.fooddelivery.data.table.cafe

import org.jetbrains.exposed.dao.id.UUIDTable

object DeliveryZoneTable : UUIDTable() {

    val isVisible = bool("isVisible")

    val cafe = reference("cafe", CafeTable)

}