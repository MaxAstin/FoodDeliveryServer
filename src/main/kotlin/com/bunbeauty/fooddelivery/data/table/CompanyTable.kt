package com.bunbeauty.fooddelivery.data.table

import org.jetbrains.exposed.dao.id.UUIDTable

object CompanyTable : UUIDTable() {

    val name = varchar("name", 512)
    val forFreeDelivery = integer("forFreeDelivery")
    val deliveryCost = integer("deliveryCost")
}