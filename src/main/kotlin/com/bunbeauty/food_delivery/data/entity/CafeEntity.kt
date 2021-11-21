package com.bunbeauty.food_delivery.data.entity

import com.bunbeauty.food_delivery.data.model.cafe.GetCafe
import com.bunbeauty.food_delivery.data.table.CafeTable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID

data class CafeEntity(
    val uuid: EntityID<String>,
) : Entity<String>(uuid) {
    val fromTime: Int by CafeTable.fromTime
    val toTime: Int by CafeTable.toTime
    val phone: String by CafeTable.phone
    val latitude: Double by CafeTable.latitude
    val longitude: Double by CafeTable.longitude
    val address: String by CafeTable.address
    val city: CityEntity by CityEntity referencedOn CafeTable.city
    val isVisible: Boolean by CafeTable.isVisible

    companion object : EntityClass<String, CafeEntity>(CafeTable)

    fun toCafe() = GetCafe(
        uuid = uuid.value,
        fromTime = fromTime,
        toTime = toTime,
        offset = city.offset,
        phone = phone,
        latitude = latitude,
        longitude = longitude,
        address = address,
        cityUuid = city.uuid.value,
        isVisible = isVisible,
    )
}