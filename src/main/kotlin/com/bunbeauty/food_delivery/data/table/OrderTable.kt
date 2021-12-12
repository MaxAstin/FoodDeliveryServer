package com.bunbeauty.food_delivery.data.table

import org.jetbrains.exposed.dao.id.UUIDTable

object OrderTable : UUIDTable() {

    val isDelivery = bool("isDelivery")
    val time = long("time")
    val code = varchar("code", 512)
    val addressDescription = varchar("addressDescription", 512)
    val comment = varchar("comment", 512).nullable()
    val deferredTime = long("deferredTime").nullable()
    val status = varchar("status", 512)
    val cafe = reference("cafe", CafeTable)
    val clientUser = reference("clientUser", ClientUserTable)

}
