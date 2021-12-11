package com.bunbeauty.food_delivery.data.table

import org.jetbrains.exposed.dao.id.UUIDTable

object ClientUserTable : UUIDTable() {

    val phoneNumber = varchar("phoneNumber", 512).uniqueIndex()
    val email = varchar("email", 512).nullable()
    val company = reference("company", CompanyTable)
}