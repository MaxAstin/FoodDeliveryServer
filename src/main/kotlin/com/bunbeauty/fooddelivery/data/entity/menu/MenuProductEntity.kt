package com.bunbeauty.fooddelivery.data.entity.menu

import com.bunbeauty.fooddelivery.data.entity.company.CompanyEntity
import com.bunbeauty.fooddelivery.data.table.menu.MenuProductCategoryTable
import com.bunbeauty.fooddelivery.data.table.menu.MenuProductTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SizedIterable
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
    var isRecommended: Boolean by MenuProductTable.isRecommended
    var isVisible: Boolean by MenuProductTable.isVisible

    var company: CompanyEntity by CompanyEntity referencedOn MenuProductTable.company
    var categories: SizedIterable<CategoryEntity> by CategoryEntity via MenuProductCategoryTable

    companion object : UUIDEntityClass<MenuProductEntity>(MenuProductTable)

}