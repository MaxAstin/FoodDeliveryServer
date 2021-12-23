package com.bunbeauty.fooddelivery.data.entity

import com.bunbeauty.fooddelivery.data.model.order.GetOrderProduct
import com.bunbeauty.fooddelivery.data.table.OrderProductTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
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

    companion object : UUIDEntityClass<OrderProductEntity>(OrderProductTable)

    fun toOrderProduct() = GetOrderProduct(
        uuid = uuid,
        count = count,
        name = name,
        newPrice = newPrice,
        oldPrice = oldPrice,
        utils = utils,
        nutrition = nutrition,
        description = description,
        comboDescription = comboDescription,
        photoLink = photoLink,
        barcode = barcode,
        menuProduct = menuProduct.toMenuProduct()
    )
}