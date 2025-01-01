package com.bunbeauty.fooddelivery.data.table

import org.jetbrains.exposed.dao.id.UUIDTable

object CompanyStatisticTable : UUIDTable() {

    val time = long("time")
    val periodType = varchar("periodType", 512)
    val orderCount = integer("orderCount")
    val orderProceeds = integer("orderProceeds")
    val company = reference("company", CompanyTable)

}