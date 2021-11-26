package com.bunbeauty.food_delivery.data.entity

import com.bunbeauty.food_delivery.data.model.city.GetCity
import com.bunbeauty.food_delivery.data.table.CityTable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class CityEntity(uuid: EntityID<UUID>) : UUIDEntity(uuid) {

    val uuid: String = uuid.value.toString()
    var name: String by CityTable.name
    var offset: Int by CityTable.offset
    var isVisible: Boolean by CityTable.isVisible

    companion object : UUIDEntityClass<CityEntity>(CityTable)

    fun toCity() = GetCity(
        uuid = uuid,
        name = name,
        offset = offset,
        isVisible = isVisible,
    )
}