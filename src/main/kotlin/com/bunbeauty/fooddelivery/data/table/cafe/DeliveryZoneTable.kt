package com.bunbeauty.fooddelivery.data.table.cafe

import org.jetbrains.exposed.dao.id.UUIDTable

object DeliveryZoneTable : UUIDTable() {

    val name = varchar("name", 512).default("")
    val minOrderCost = integer("minOrderCost").nullable()
    val normalDeliveryCost = integer("normalDeliveryCost")
    val forLowDeliveryCost = integer("forLowDeliveryCost").nullable()
    val lowDeliveryCost = integer("lowDeliveryCost").nullable()
    val isVisible = bool("isVisible")

    val cafe = reference("cafe", CafeTable)

}