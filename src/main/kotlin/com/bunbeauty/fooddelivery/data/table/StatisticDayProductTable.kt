package com.bunbeauty.fooddelivery.data.table

import org.jetbrains.exposed.dao.id.UUIDTable

object StatisticDayProductTable : UUIDTable() {

    val name = varchar("name", 512)
    val photoLink = varchar("photoLink", 512)
    val productCount = integer("productCount")
    val productProceeds = integer("productProceeds")
    val statisticDay = reference("statisticDay", StatisticDayTable)

}
