package com.bunbeauty.fooddelivery.data.table.cafe

import org.jetbrains.exposed.dao.id.UUIDTable

object DeliveryZoneTable : UUIDTable() {

    val minOrderCost = integer("minOrderCost").nullable()
    val normalDeliveryCost = integer("normalDeliveryCost").default(0)
    val forLowDeliveryCost = integer("forLowDeliveryCost").nullable()
    val lowDeliveryCost = integer("lowDeliveryCost").nullable()
    val isVisible = bool("isVisible")

    val cafe = reference("cafe", CafeTable)

}