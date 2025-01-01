package com.bunbeauty.fooddelivery.data.table

import org.jetbrains.exposed.dao.id.UUIDTable

object RequestTable : UUIDTable() {

    val ip = varchar("ip", 512)
    val name = varchar("name", 512)
    val time = long("time")
}
