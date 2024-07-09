package com.bunbeauty.fooddelivery.data.entity.order

import com.bunbeauty.fooddelivery.data.table.order.OrderProductAdditionTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class OrderProductAdditionEntity(uuid: EntityID<UUID>) : UUIDEntity(uuid) {

    val uuid: String = uuid.value.toString()
    var name: String by OrderProductAdditionTable.name
    var price: Int? by OrderProductAdditionTable.price
    var priority: Int by OrderProductAdditionTable.priority

    var orderProduct: OrderProductEntity by OrderProductEntity referencedOn OrderProductAdditionTable.orderProduct

    companion object : UUIDEntityClass<OrderProductAdditionEntity>(OrderProductAdditionTable)

}