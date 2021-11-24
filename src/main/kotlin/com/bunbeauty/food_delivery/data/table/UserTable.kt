package com.bunbeauty.food_delivery.data.table

import com.bunbeauty.food_delivery.data.enums.UserRole
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

object UserTable: IdTable<String>() {

    val uuid = varchar("uuid", 512)
    val username = varchar("username", 512)
    val passwordHash = varchar("passwordHash", 512)
    val role = enumeration("role", UserRole::class)
    val company = reference("company", CompanyTable)

    override val id: Column<EntityID<String>> = uuid.entityId()
}