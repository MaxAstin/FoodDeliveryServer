package com.bunbeauty.fooddelivery.data.entity.menu

import com.bunbeauty.fooddelivery.data.table.menu.AdditionGroupTable
import com.bunbeauty.fooddelivery.data.table.menu.AdditionTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SizedIterable
import java.util.*

class AdditionGroupEntity(uuid: EntityID<UUID>) : UUIDEntity(uuid) {

    val uuid: String = uuid.value.toString()
    var name: String by AdditionGroupTable.name
    var singleChoice: Boolean by AdditionGroupTable.singleChoice
    var priority: Int by AdditionTable.priority
    var isVisible: Boolean by AdditionGroupTable.isVisible

    val additions: SizedIterable<AdditionEntity> by AdditionEntity referrersOn AdditionTable.group

    companion object : UUIDEntityClass<AdditionGroupEntity>(AdditionGroupTable)

}