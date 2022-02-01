package com.bunbeauty.fooddelivery.data.table

import org.jetbrains.exposed.dao.id.UUIDTable

object OrderTable : UUIDTable() {

    val isDelivery = bool("isDelivery")
    val time = long("time")
    val timeZone = varchar("timeZone", 512).default("UTC+3")
    val code = varchar("code", 512)
    val addressDescription = varchar("addressDescription", 512)
    val comment = varchar("comment", 512).nullable()
    val deferredTime = long("deferredTime").nullable()
    val status = varchar("status", 512)
    val deliveryCost = integer("deliveryCost").nullable()
    val cafe = reference("cafe", CafeTable)
    val company = reference("company", CompanyTable)
    val clientUser = reference("clientUser", ClientUserTable)

}
