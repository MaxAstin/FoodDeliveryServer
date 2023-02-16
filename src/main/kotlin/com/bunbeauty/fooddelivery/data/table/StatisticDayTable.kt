package com.bunbeauty.fooddelivery.data.table

import org.jetbrains.exposed.dao.id.UUIDTable

object StatisticDayTable : UUIDTable() {

    val time = long("time")
    val orderCount = integer("orderCount")
    val orderProceeds = integer("orderProceeds")
    val company = reference("company", CompanyTable)

}