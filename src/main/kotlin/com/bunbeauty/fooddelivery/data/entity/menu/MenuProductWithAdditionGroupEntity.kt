package com.bunbeauty.fooddelivery.data.entity.menu

import com.bunbeauty.fooddelivery.data.table.menu.AdditionGroupTable
import com.bunbeauty.fooddelivery.data.table.menu.MenuProductTable
import com.bunbeauty.fooddelivery.data.table.menu.MenuProductToAdditionGroupTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.ColumnSet
import org.jetbrains.exposed.sql.ResultRow
import java.util.*

class MenuProductWithAdditionGroupEntity(uuid: EntityID<UUID>) : UUIDEntity(uuid) {

    val uuid: String = uuid.value.toString()
    var menuProduct by MenuProductEntity referencedOn MenuProductToAdditionGroupTable.menuProduct
    var additionGroup by AdditionGroupEntity referencedOn MenuProductToAdditionGroupTable.additionGroup

    companion object : UUIDEntityClass<MenuProductWithAdditionGroupEntity>(MenuProductToAdditionGroupTable) {
        override val dependsOnTables: ColumnSet =
            MenuProductTable.innerJoin(MenuProductToAdditionGroupTable)
                .innerJoin(AdditionGroupTable)

        override fun createInstance(entityId: EntityID<UUID>, row: ResultRow?): MenuProductWithAdditionGroupEntity {
            row?.getOrNull(MenuProductTable.id)?.let { id ->
                MenuProductEntity.wrap(id, row)
            }
            row?.getOrNull(AdditionGroupTable.id)?.let { id ->
                AdditionGroupEntity.wrap(id, row)
            }

            return super.createInstance(entityId, row)
        }
    }
}
