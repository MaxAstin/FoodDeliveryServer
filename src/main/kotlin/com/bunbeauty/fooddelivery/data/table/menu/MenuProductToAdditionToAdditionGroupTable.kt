package com.bunbeauty.fooddelivery.data.table.menu

import org.jetbrains.exposed.sql.Table

object MenuProductToAdditionToAdditionGroupTable: Table() {

    val isSelected = bool("isSelected")
    val isVisible = bool("isVisible")

    val menuProduct = reference("menuProduct", MenuProductTable)
    val additionGroup = reference("additionGroup", AdditionGroupTable)
    val addition = reference("addition", AdditionTable)

    override val primaryKey = PrimaryKey(menuProduct, additionGroup, addition)

}