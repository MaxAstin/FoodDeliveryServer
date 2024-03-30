package com.bunbeauty.fooddelivery.data.entity.cafe

import com.bunbeauty.fooddelivery.data.table.cafe.DeliveryZonePointTable
import com.bunbeauty.fooddelivery.data.table.cafe.DeliveryZoneTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SizedIterable
import java.util.*

class DeliveryZoneEntity(uuid: EntityID<UUID>) : UUIDEntity(uuid) {

    val uuid: String = uuid.value.toString()
    var minOrderCost: Int? by DeliveryZoneTable.minOrderCost
    var normalDeliveryCost: Int by DeliveryZoneTable.normalDeliveryCost
    var forLowDeliveryCost: Int? by DeliveryZoneTable.forLowDeliveryCost
    var lowDeliveryCost: Int? by DeliveryZoneTable.lowDeliveryCost
    var isVisible: Boolean by DeliveryZoneTable.isVisible

    val points: SizedIterable<DeliveryZonePointEntity> by DeliveryZonePointEntity referrersOn DeliveryZonePointTable.zone
    var cafe: CafeEntity by CafeEntity referencedOn DeliveryZoneTable.cafe

    companion object : UUIDEntityClass<DeliveryZoneEntity>(DeliveryZoneTable)

}