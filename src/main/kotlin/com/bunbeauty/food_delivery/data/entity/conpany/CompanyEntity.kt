package com.bunbeauty.food_delivery.data.entity.conpany

import com.bunbeauty.food_delivery.data.model.company.GetCompany
import com.bunbeauty.food_delivery.data.table.CompanyTable
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class CompanyEntity(
    var uuid: EntityID<UUID>,
) : UUIDEntity(uuid) {

    var name: String by CompanyTable.name

    companion object : EntityClass<UUID, CompanyEntity>(CompanyTable)

    fun toModel() = GetCompany(
        uuid = uuid.value.toString(),
        name = name
    )
}