package com.bunbeauty.fooddelivery.data.table

import org.jetbrains.exposed.dao.id.UUIDTable

object AddressTable : UUIDTable() {

    val house = varchar("house", 512)
    val flat = varchar("flat", 512).nullable()
    val entrance = varchar("entrance", 512).nullable()
    val floor = varchar("floor", 512).nullable()
    val comment = varchar("comment", 512).nullable()
    val street = reference("street", StreetTable)
    val clientUser = reference("clientUser", ClientUserTable)
    val isVisible = bool("isVisible")

}