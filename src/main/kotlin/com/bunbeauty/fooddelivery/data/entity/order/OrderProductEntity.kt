package com.bunbeauty.fooddelivery.data.entity.order

import com.bunbeauty.fooddelivery.data.entity.menu.MenuProductEntity
import com.bunbeauty.fooddelivery.data.table.order.OrderProductAdditionTable
import com.bunbeauty.fooddelivery.data.table.order.OrderProductTable
import com.bunbeauty.fooddelivery.domain.model.statistic.GetStatisticOrderProduct
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SizedIterable
import java.util.*

class OrderProductEntity(uuid: EntityID<UUID>) : UUIDEntity(uuid) {

    val uuid: String = uuid.value.toString()
    var count: Int by OrderProductTable.count
    var name: String by OrderProductTable.name
    var newPrice: Int by OrderProductTable.newPrice
    var oldPrice: Int? by OrderProductTable.oldPrice
    var utils: String? by OrderProductTable.utils
    var nutrition: Int? by OrderProductTable.nutrition
    var description: String by OrderProductTable.description
    var comboDescription: String? by OrderProductTable.comboDescription
    var photoLink: String by OrderProductTable.photoLink
    var barcode: Int by OrderProductTable.barcode

    var menuProduct: MenuProductEntity by MenuProductEntity referencedOn OrderProductTable.menuProduct
    var order: OrderEntity by OrderEntity referencedOn OrderProductTable.order
    val additions: SizedIterable<OrderProductAdditionEntity> by OrderProductAdditionEntity referrersOn OrderProductAdditionTable.orderProduct

    companion object : UUIDEntityClass<OrderProductEntity>(OrderProductTable)

    fun toStatisticOrderProduct() = GetStatisticOrderProduct(
        uuid = uuid,
        count = count,
        menuProductUuid = menuProduct.uuid,
        name = name,
        newPrice = newPrice,
        photoLink = photoLink,
    )
}