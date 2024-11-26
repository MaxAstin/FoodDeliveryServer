package com.bunbeauty.fooddelivery.data.table.menu

import com.bunbeauty.fooddelivery.data.table.CompanyTable
import org.jetbrains.exposed.dao.id.UUIDTable

object AdditionTable : UUIDTable() {

    val name = varchar("name", 512)
    val fullName = varchar("fullName", 512).nullable()
    val price = integer("price").nullable()
    val photoLink = varchar("photoLink", 512)
    val tag = varchar("tag", 512).nullable()
    val priority = integer("priority")
    val isVisible = bool("isVisible")

    val company = reference("company", CompanyTable)

}