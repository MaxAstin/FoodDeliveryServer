package com.bunbeauty.food_delivery.data.entity

import com.bunbeauty.food_delivery.data.model.city.GetCity
import com.bunbeauty.food_delivery.data.table.CityTable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID

data class CityEntity(
    val uuid: EntityID<String>,
) : Entity<String>(uuid) {
    val name: String by CityTable.name
    val offset: Int by CityTable.offset
    val isVisible: Boolean by CityTable.isVisible

    companion object : EntityClass<String, CityEntity>(CityTable)

    fun toCity() = GetCity(
        uuid = uuid.value,
        name = name,
        offset = offset,
        isVisible = isVisible,
    )
}