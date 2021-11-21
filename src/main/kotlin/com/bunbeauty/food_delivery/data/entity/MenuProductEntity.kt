package com.bunbeauty.food_delivery.data.entity

import com.bunbeauty.food_delivery.data.model.menu_product.GetMenuProduct
import com.bunbeauty.food_delivery.data.table.MenuProductTable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID

data class MenuProductEntity(
    val entityId: EntityID<String>,
) : Entity<String>(entityId) {
    val uuid: String = entityId.value
    val name: String by MenuProductTable.name
    val newPrice: Int by MenuProductTable.newPrice
    val oldPrice: Int? by MenuProductTable.oldPrice
    val utils: String? by MenuProductTable.utils
    val nutrition: Int? by MenuProductTable.nutrition
    val description: String by MenuProductTable.description
    val comboDescription: String? by MenuProductTable.comboDescription
    val photoLink: String by MenuProductTable.photoLink
    val barcode: Int by MenuProductTable.barcode
    val isVisible: Boolean by MenuProductTable.isVisible

    companion object : EntityClass<String, MenuProductEntity>(MenuProductTable)

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