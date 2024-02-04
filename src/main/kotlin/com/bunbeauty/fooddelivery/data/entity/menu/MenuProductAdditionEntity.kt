package com.bunbeauty.fooddelivery.data.entity.menu

import com.bunbeauty.fooddelivery.data.table.menu.AdditionGroupTable
import com.bunbeauty.fooddelivery.data.table.menu.AdditionTable
import com.bunbeauty.fooddelivery.data.table.menu.MenuProductTable
import com.bunbeauty.fooddelivery.data.table.menu.MenuProductToAdditionToAdditionGroupTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.ColumnSet
import org.jetbrains.exposed.sql.ResultRow
import java.util.*

class MenuProductWithAdditionEntity(uuid: EntityID<UUID>) : UUIDEntity(uuid) {

    var menuProduct by MenuProductEntity referencedOn MenuProductToAdditionToAdditionGroupTable.menuProduct
    var additionGroup by AdditionGroupEntity referencedOn MenuProductToAdditionToAdditionGroupTable.additionGroup
    var addition by AdditionEntity referencedOn MenuProductToAdditionToAdditionGroupTable.addition
    var isSelected by MenuProductToAdditionToAdditionGroupTable.isSelected
    var isVisible by MenuProductToAdditionToAdditionGroupTable.isVisible

    companion object : UUIDEntityClass<MenuProductWithAdditionEntity>(MenuProductToAdditionToAdditionGroupTable) {
        override val dependsOnTables: ColumnSet =
            MenuProductToAdditionToAdditionGroupTable.innerJoin(MenuProductTable)
                .innerJoin(AdditionGroupTable)
                .innerJoin(AdditionTable)

        override fun createInstance(entityId: EntityID<UUID>, row: ResultRow?): MenuProductWithAdditionEntity {
            row?.getOrNull(MenuProductTable.id)?.let { id ->
                MenuProductEntity.wrap(id, row)
            }
            row?.getOrNull(MenuProductTable.id)?.let { id ->
                AdditionGroupEntity.wrap(id, row)
            }
            row?.getOrNull(MenuProductTable.id)?.let { id ->
                AdditionEntity.wrap(id, row)
            }

            return super.createInstance(entityId, row)
        }
    }

}