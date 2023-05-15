package com.bunbeauty.fooddelivery.data.entity.company

import com.bunbeauty.fooddelivery.data.model.company.link.GetLink
import com.bunbeauty.fooddelivery.data.table.LinkTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.UUID

class LinkEntity(uuid: EntityID<UUID>) : UUIDEntity(uuid) {

    val uuid: String = uuid.value.toString()
    var type: String by LinkTable.type
    var value: String by LinkTable.value

    var company: CompanyEntity by CompanyEntity referencedOn LinkTable.company

    companion object : UUIDEntityClass<LinkEntity>(LinkTable)

    fun toLink() = GetLink(
        uuid = uuid,
        type = type,
        value = value,
    )
}