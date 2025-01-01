package com.bunbeauty.fooddelivery.data.table.menu

import org.jetbrains.exposed.dao.id.UUIDTable

object MenuProductToAdditionGroupToAdditionTable : UUIDTable() {

    val isSelected = bool("isSelected")
    val isVisible = bool("isVisible")

    val menuProductToAdditionGroup = reference("menuProductToAdditionGroup", MenuProductToAdditionGroupTable)
    val addition = reference("addition", AdditionTable)
}
