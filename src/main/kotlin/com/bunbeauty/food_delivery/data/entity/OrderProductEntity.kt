package com.bunbeauty.food_delivery.data.entity

import com.bunbeauty.food_delivery.data.model.order.GetOrderProduct
import com.bunbeauty.food_delivery.data.table.OrderProductTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class OrderProductEntity(uuid: EntityID<UUID>) : UUIDEntity(uuid) {

    val uuid: String = uuid.value.toString()
    var count: Int by OrderProductTable.count
    var menuProduct: MenuProductEntity by MenuProductEntity referencedOn OrderProductTable.menuProduct
    var order: OrderEntity by OrderEntity referencedOn OrderProductTable.order

    companion object : UUIDEntityClass<OrderProductEntity>(OrderProductTable)

    fun toOrderProduct() = GetOrderProduct(
        menuProduct = menuProduct.toMenuProduct(),
        count = count,
    )
}