package com.bunbeauty.fooddelivery.data.entity

import com.bunbeauty.fooddelivery.data.model.cafe.GetCafe
import com.bunbeauty.fooddelivery.data.table.CafeTable
import com.bunbeauty.fooddelivery.data.table.OrderTable
import com.bunbeauty.fooddelivery.data.table.StreetTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SizedIterable
import java.util.UUID

class CafeEntity(uuid: EntityID<UUID>) : UUIDEntity(uuid) {

    val uuid: String = uuid.value.toString()
    var fromTime: Int by CafeTable.fromTime
    var toTime: Int by CafeTable.toTime
    var phoneNumber: String by CafeTable.phoneNumber
    var latitude: Double by CafeTable.latitude
    var longitude: Double by CafeTable.longitude
    var address: String by CafeTable.address
    var codeCounter: Int by CafeTable.codeCounter
    var city: CityEntity by CityEntity referencedOn CafeTable.city
    var isVisible: Boolean by CafeTable.isVisible

    val streets: SizedIterable<StreetEntity> by StreetEntity referrersOn StreetTable.cafe
    val orders: SizedIterable<OrderEntity> by OrderEntity referrersOn OrderTable.cafe

    companion object : UUIDEntityClass<CafeEntity>(CafeTable)

    fun toCafe() = GetCafe(
        uuid = uuid,
        fromTime = fromTime,
        toTime = toTime,
        offset = 0,
        phone = phoneNumber,
        latitude = latitude,
        longitude = longitude,
        address = address,
        cityUuid = city.uuid,
        isVisible = isVisible,
    )
}