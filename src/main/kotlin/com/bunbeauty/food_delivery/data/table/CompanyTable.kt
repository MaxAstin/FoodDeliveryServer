package com.bunbeauty.food_delivery.data.table

import org.jetbrains.exposed.dao.id.UUIDTable

object CompanyTable : UUIDTable() {

    val name = varchar("name", 512)

}