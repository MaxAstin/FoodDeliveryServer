package com.bunbeauty.fooddelivery.data.table

import org.jetbrains.exposed.dao.id.UUIDTable

object TestClientUserPhoneTable : UUIDTable() {

    val phoneNumber = varchar("phoneNumber", 512)
    val code = varchar("code", 512)
}
