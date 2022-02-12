package com.bunbeauty.fooddelivery.data.table

import org.jetbrains.exposed.dao.id.UUIDTable

object ClientUserLoginSessionTable : UUIDTable() {

    val phoneNumber = varchar("phoneNumber", 512)
    val time = long("time")
    val code = varchar("code", 512)

}