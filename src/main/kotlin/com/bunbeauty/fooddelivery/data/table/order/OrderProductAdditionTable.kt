package com.bunbeauty.fooddelivery.data.table.order

import org.jetbrains.exposed.dao.id.UUIDTable

object OrderProductAdditionTable : UUIDTable() {

    val name = varchar("name", 512)
    val price = integer("price").nullable()
    val priority = integer("priority").default(1)
    val orderProduct = reference("orderProduct", OrderProductTable)

}