package com.bunbeauty.fooddelivery.data.table.order

import org.jetbrains.exposed.dao.id.UUIDTable

object OrderProductAdditionTable : UUIDTable() {

    val name = OrderProductTable.varchar("name", 512)
    val price = integer("price").nullable()
    val orderProduct = reference("orderProduct", OrderProductTable)

}