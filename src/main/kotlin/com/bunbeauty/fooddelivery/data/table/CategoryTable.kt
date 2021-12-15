package com.bunbeauty.fooddelivery.data.table

import org.jetbrains.exposed.dao.id.UUIDTable

object CategoryTable : UUIDTable() {

    val name = varchar("name", 512)
    val company = reference("company", CompanyTable)
}