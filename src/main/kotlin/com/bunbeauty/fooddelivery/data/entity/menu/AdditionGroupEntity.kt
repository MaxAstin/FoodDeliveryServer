package com.bunbeauty.fooddelivery.data.entity.menu

import com.bunbeauty.fooddelivery.data.entity.company.CompanyEntity
import com.bunbeauty.fooddelivery.data.table.menu.AdditionGroupTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class AdditionGroupEntity(uuid: EntityID<UUID>) : UUIDEntity(uuid) {

    val uuid: String = uuid.value.toString()
    var name: String by AdditionGroupTable.name
    var singleChoice: Boolean by AdditionGroupTable.singleChoice
    var priority: Int by AdditionGroupTable.priority
    var isVisible: Boolean by AdditionGroupTable.isVisible

    var company: CompanyEntity by CompanyEntity referencedOn AdditionGroupTable.company

    companion object : UUIDEntityClass<AdditionGroupEntity>(AdditionGroupTable)

}