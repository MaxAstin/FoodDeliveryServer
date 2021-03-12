package com.bunbeauty.fooddelivery.data.table

import org.jetbrains.exposed.dao.id.UUIDTable

object CompanyTable : UUIDTable() {

    val name = varchar("name", 512).uniqueIndex()
    val forFreeDelivery = integer("forFreeDelivery")
    val deliveryCost = integer("deliveryCost")
    val forceUpdateVersion = integer("forceUpdateVersion").default(0)
}