package com.bunbeauty.food_delivery.data.entity

import com.bunbeauty.food_delivery.data.model.menu_product.GetMenuProduct
import com.bunbeauty.food_delivery.data.table.MenuProductTable
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class MenuProductEntity(uuid: EntityID<UUID>) : UUIDEntity(uuid) {

    val uuid: String = uuid.value.toString()
    var name: String by MenuProductTable.name
    var newPrice: Int by MenuProductTable.newPrice
    var oldPrice: Int? by MenuProductTable.oldPrice
    var utils: String? by MenuProductTable.utils
    var nutrition: Int? by MenuProductTable.nutrition
    var description: String by MenuProductTable.description
    var comboDescription: String? by MenuProductTable.comboDescription
    var photoLink: String by MenuProductTable.photoLink
    var barcode: Int by MenuProductTable.barcode
    var isVisible: Boolean by MenuProductTable.isVisible

    companion object : UUIDEntityClass<MenuProductEntity>(MenuProductTable)

    fun toMenuProduct() = GetMenuProduct(
        uuid = uuid,
        name = name,
        newPrice = newPrice,
        oldPrice = oldPrice,
        utils = utils,
        nutrition = nutrition,
        description = description,
        comboDescription = comboDescription,
        photoLink = photoLink,
        barcode = barcode,
        isVisible = isVisible,
    )
}