package com.bunbeauty.fooddelivery.data.table

import com.bunbeauty.fooddelivery.data.table.cafe.CafeTable
import org.jetbrains.exposed.dao.id.UUIDTable

object CafeStatisticTable : UUIDTable() {

    val time = long("time")
    val periodType = varchar("periodType", 512)
    val orderCount = integer("orderCount")
    val orderProceeds = integer("orderProceeds")
    val cafe = reference("cafe", CafeTable)

}