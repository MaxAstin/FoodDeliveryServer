package com.bunbeauty.fooddelivery.data.table

import org.jetbrains.exposed.dao.id.UUIDTable

object AddressTableV2 : UUIDTable() {

    val streetFiasId = varchar("streetFiasId", 512)
    val streetName = varchar("streetName", 512)
    val house = varchar("house", 512)
    val flat = varchar("flat", 512).nullable()
    val entrance = varchar("entrance", 512).nullable()
    val floor = varchar("floor", 512).nullable()
    val comment = varchar("comment", 512).nullable()
    val isVisible = bool("isVisible")

    val clientUser = reference("clientUser", ClientUserTable)

}