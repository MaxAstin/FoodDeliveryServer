package com.bunbeauty.food_delivery.data.table

import com.bunbeauty.food_delivery.data.enums.UserRole
import org.jetbrains.exposed.dao.id.UUIDTable

object UserTable : UUIDTable() {

    val username = varchar("username", 512).uniqueIndex()
    val passwordHash = varchar("passwordHash", 512)
    val role = enumeration("role", UserRole::class)
    val company = reference("company", CompanyTable)
}