package com.bunbeauty.fooddelivery.data.table.menu

import org.jetbrains.exposed.dao.id.UUIDTable

object MenuProductToAdditionGroupTable : UUIDTable() {

    val menuProduct = reference("menuProduct", MenuProductTable)
    val additionGroup = reference("additionGroup", AdditionGroupTable)

}