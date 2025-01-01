package com.bunbeauty.fooddelivery.data.table

import org.jetbrains.exposed.dao.id.UUIDTable

object LinkTable : UUIDTable() {

    val type = LinkTable.varchar("type", 512)
    val value = LinkTable.varchar("value", 512)
    val company = LinkTable.reference("company", CompanyTable)

}