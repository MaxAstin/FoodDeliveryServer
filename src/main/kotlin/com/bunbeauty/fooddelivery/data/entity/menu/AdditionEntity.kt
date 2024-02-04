package com.bunbeauty.fooddelivery.data.entity.menu

import com.bunbeauty.fooddelivery.data.table.menu.AdditionTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class AdditionEntity(uuid: EntityID<UUID>) : UUIDEntity(uuid) {

    val uuid: String = uuid.value.toString()
    var name: String by AdditionTable.name
    var fullName: String? by AdditionTable.fullName
    var price: Int? by AdditionTable.price
    var photoLink: String by AdditionTable.photoLink
    var priority: Int by AdditionTable.priority
    var isVisible: Boolean by AdditionTable.isVisible

    companion object : UUIDEntityClass<AdditionEntity>(AdditionTable)

}