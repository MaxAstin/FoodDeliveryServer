package com.bunbeauty.fooddelivery.data.table.menu

import org.jetbrains.exposed.dao.id.UUIDTable

object MenuProductToAdditionToAdditionGroupTable : UUIDTable() {

    val isSelected = bool("isSelected")
    val isVisible = bool("isVisible")

    val menuProduct = reference("menuProduct", MenuProductTable)
    val additionGroup = reference("additionGroup", AdditionGroupTable)
    val addition = reference("addition", AdditionTable)

}