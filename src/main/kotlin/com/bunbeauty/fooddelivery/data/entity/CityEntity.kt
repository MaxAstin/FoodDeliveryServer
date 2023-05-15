package com.bunbeauty.fooddelivery.data.entity

import com.bunbeauty.fooddelivery.data.entity.company.CompanyEntity
import com.bunbeauty.fooddelivery.data.model.city.GetCity
import com.bunbeauty.fooddelivery.data.table.CafeTable
import com.bunbeauty.fooddelivery.data.table.CityTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SizedIterable
import java.util.UUID

class CityEntity(uuid: EntityID<UUID>) : UUIDEntity(uuid) {

    val uuid: String = uuid.value.toString()
    var name: String by CityTable.name
    var timeZone: String by CityTable.timeZone
    var company: CompanyEntity by CompanyEntity referencedOn CityTable.company
    var isVisible: Boolean by CityTable.isVisible

    val cafes: SizedIterable<CafeEntity> by CafeEntity referrersOn CafeTable.city

    companion object : UUIDEntityClass<CityEntity>(CityTable)

    fun toCity() = GetCity(
        uuid = uuid,
        name = name,
        timeZone = timeZone,
        company = company.toCompany(),
        isVisible = isVisible,
    )
}