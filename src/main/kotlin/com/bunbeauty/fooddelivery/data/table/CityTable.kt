package com.bunbeauty.fooddelivery.data.table

import org.jetbrains.exposed.dao.id.UUIDTable

object CityTable : UUIDTable() {

    val name = varchar("name", 512)
    val timeZone = varchar("timeZone", 512).default("UTC+3")
    val company = reference("company", CompanyTable)
    val isVisible = bool("isVisible")
}
