package com.bunbeauty.fooddelivery.data.entity.cafe

import com.bunbeauty.fooddelivery.data.table.cafe.DeliveryZonePointTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class DeliveryZonePointEntity(uuid: EntityID<UUID>) : UUIDEntity(uuid) {

    val uuid: String = uuid.value.toString()
    var order: Int by DeliveryZonePointTable.order
    var latitude: Double by DeliveryZonePointTable.latitude
    var longitude: Double by DeliveryZonePointTable.longitude
    var isVisible: Boolean by DeliveryZonePointTable.isVisible

    var zone: DeliveryZoneEntity by DeliveryZoneEntity referencedOn DeliveryZonePointTable.zone

    companion object : UUIDEntityClass<DeliveryZonePointEntity>(DeliveryZonePointTable)

}