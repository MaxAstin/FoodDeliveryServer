package com.bunbeauty.fooddelivery.data.table

import org.jetbrains.exposed.dao.id.UUIDTable

object ClientAuthSessionTable : UUIDTable() {

    val phoneNumber = varchar("phoneNumber", 512)
    val time = long("time")
    val attemptsLeft = integer("attemptsLeft")
    val isConfirmed = bool("isConfirmed")
    val company = reference("company", CompanyTable)
}
