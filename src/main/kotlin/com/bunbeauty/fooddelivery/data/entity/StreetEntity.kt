package com.bunbeauty.fooddelivery.data.entity

import com.bunbeauty.fooddelivery.data.entity.cafe.CafeEntity
import com.bunbeauty.fooddelivery.data.table.StreetTable
import com.bunbeauty.fooddelivery.domain.model.street.GetStreet
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class StreetEntity(uuid: EntityID<UUID>) : UUIDEntity(uuid) {

    val uuid: String = uuid.value.toString()
    var name: String by StreetTable.name
    var latitude: Double by StreetTable.latitude
    var longitude: Double by StreetTable.longitude
    var isVisible: Boolean by StreetTable.isVisible

    var cafe: CafeEntity by CafeEntity referencedOn StreetTable.cafe

    companion object : UUIDEntityClass<StreetEntity>(StreetTable)

    fun toStreet() = GetStreet(
        uuid = uuid,
        name = name,
        cityUuid = cafe.city.uuid,
        cafeUuid = cafe.uuid,
        isVisible = isVisible,
    )
}