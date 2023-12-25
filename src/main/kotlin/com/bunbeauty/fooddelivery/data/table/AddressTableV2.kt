package com.bunbeauty.fooddelivery.data.table

import org.jetbrains.exposed.dao.id.UUIDTable

object AddressV2Table : UUIDTable() {

    val streetFiasId = varchar("streetFiasId", 512)
    val streetName = varchar("streetName", 512)
    val streetLatitude = double("streetLatitude").default(0.0)
    val streetLongitude = double("streetLongitude").default(0.0)
    val house = varchar("house", 512)
    val flat = varchar("flat", 512).nullable()
    val entrance = varchar("entrance", 512).nullable()
    val floor = varchar("floor", 512).nullable()
    val comment = varchar("comment", 512).nullable()
    val isVisible = bool("isVisible")

    val clientUser = reference("clientUser", ClientUserTable)
    val city = reference("city", CityTable)

}