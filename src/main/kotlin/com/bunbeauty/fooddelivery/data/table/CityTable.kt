package com.bunbeauty.fooddelivery.data.table

import org.jetbrains.exposed.dao.id.UUIDTable

object CityTable : UUIDTable() {

    val name = varchar("name", 512)
    val offset = integer("offset")
    val company = reference("company", CompanyTable)
    val isVisible = bool("isVisible")

}
