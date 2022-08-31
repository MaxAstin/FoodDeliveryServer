package com.bunbeauty.fooddelivery.data.table

import com.bunbeauty.fooddelivery.data.enums.UserRole
import org.jetbrains.exposed.dao.id.UUIDTable

object UserTable : UUIDTable() {

    val username = varchar("username", 512)
    val passwordHash = varchar("passwordHash", 512)
    val role = enumeration("role", UserRole::class)
    val city = reference("city", CityTable)
}