package com.bunbeauty.fooddelivery.data.table.address

import com.bunbeauty.fooddelivery.data.table.CityTable
import com.bunbeauty.fooddelivery.data.table.ClientUserTable
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

    @Deprecated("Not need city because we have cafe uuid")
    val city = reference("city", CityTable)

    val deliveryZoneUuid = varchar("deliveryZoneUuid", 500).default("")
}
