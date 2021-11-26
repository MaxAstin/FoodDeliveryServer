package com.bunbeauty.food_delivery.data.table

import org.jetbrains.exposed.dao.id.UUIDTable

object OrderTable : UUIDTable() {

    val isDelivery = bool("isDelivery")
    val code = varchar("code", 512)
    val address = varchar("address", 512)
    val comment = varchar("comment", 512).nullable()
    val deferredTime = long("deferredTime").nullable()
    val status = varchar("status", 512)
    val addressUuid = varchar("addressUuid", 512).nullable()
    val cafeUuid = varchar("cafeUuid", 512)
    val userUuid = varchar("userUuid", 512)

}
