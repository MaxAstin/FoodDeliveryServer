package com.bunbeauty.fooddelivery.data.entity.menu

import com.bunbeauty.fooddelivery.data.table.menu.AdditionTable
import com.bunbeauty.fooddelivery.data.table.menu.MenuProductToAdditionGroupTable
import com.bunbeauty.fooddelivery.data.table.menu.MenuProductToAdditionGroupToAdditionTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.ColumnSet
import org.jetbrains.exposed.sql.ResultRow
import java.util.*

class MenuProductWithAdditionGroupWithAdditionEntity(uuid: EntityID<UUID>) : UUIDEntity(uuid) {

    val uuid: String = uuid.value.toString()
    var menuProductWithAdditionGroup by MenuProductWithAdditionGroupEntity referencedOn MenuProductToAdditionGroupToAdditionTable.menuProductToAdditionGroup
    var addition by AdditionEntity referencedOn MenuProductToAdditionGroupToAdditionTable.addition
    var isSelected by MenuProductToAdditionGroupToAdditionTable.isSelected
    var isVisible by MenuProductToAdditionGroupToAdditionTable.isVisible

    companion object :
        UUIDEntityClass<MenuProductWithAdditionGroupWithAdditionEntity>(MenuProductToAdditionGroupToAdditionTable) {
        override val dependsOnTables: ColumnSet =
            MenuProductToAdditionGroupTable.innerJoin(MenuProductToAdditionGroupToAdditionTable)
                .innerJoin(AdditionTable)

        override fun createInstance(
            entityId: EntityID<UUID>,
            row: ResultRow?
        ): MenuProductWithAdditionGroupWithAdditionEntity {
            row?.getOrNull(MenuProductToAdditionGroupTable.id)?.let { id ->
                MenuProductWithAdditionGroupEntity.wrap(id, row)
            }
            row?.getOrNull(AdditionTable.id)?.let { id ->
                AdditionEntity.wrap(id, row)
            }

            return super.createInstance(entityId, row)
        }
    }
}
