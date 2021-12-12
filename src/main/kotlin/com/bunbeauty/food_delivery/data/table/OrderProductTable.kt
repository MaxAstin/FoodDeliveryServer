package com.bunbeauty.food_delivery.data.table

import org.jetbrains.exposed.dao.id.UUIDTable

object OrderProductTable : UUIDTable() {

    val count = integer("count")
    val menuProduct = reference("menuProduct", MenuProductTable)
    val order = reference("order", OrderTable)

}
