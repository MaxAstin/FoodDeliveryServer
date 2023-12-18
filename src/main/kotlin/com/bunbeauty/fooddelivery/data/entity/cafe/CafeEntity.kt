package com.bunbeauty.fooddelivery.data.entity.cafe

import com.bunbeauty.fooddelivery.data.entity.CityEntity
import com.bunbeauty.fooddelivery.data.entity.StreetEntity
import com.bunbeauty.fooddelivery.data.table.StreetTable
import com.bunbeauty.fooddelivery.data.table.cafe.CafeTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SizedIterable
import java.util.*

class CafeEntity(uuid: EntityID<UUID>) : UUIDEntity(uuid) {

    val uuid: String = uuid.value.toString()
    var fromTime: Int by CafeTable.fromTime
    var toTime: Int by CafeTable.toTime
    var offset: Int by CafeTable.offset
    var phoneNumber: String by CafeTable.phoneNumber
    var latitude: Double by CafeTable.latitude
    var longitude: Double by CafeTable.longitude
    var address: String by CafeTable.address
    var codeCounter: Int by CafeTable.codeCounter
    var city: CityEntity by CityEntity referencedOn CafeTable.city
    var isVisible: Boolean by CafeTable.isVisible

    val streets: SizedIterable<StreetEntity> by StreetEntity referrersOn StreetTable.cafe

    companion object : UUIDEntityClass<CafeEntity>(CafeTable)

}