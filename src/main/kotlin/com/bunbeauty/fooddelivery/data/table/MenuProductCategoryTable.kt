package com.bunbeauty.fooddelivery.data.table

import com.bunbeauty.fooddelivery.data.table.menu.CategoryTable
import com.bunbeauty.fooddelivery.data.table.menu.MenuProductTable
import org.jetbrains.exposed.sql.Table

object MenuProductCategoryTable: Table() {

    val menuProduct = reference("menuProduct", MenuProductTable)
    val category = reference("category", CategoryTable)

    override val primaryKey = PrimaryKey(menuProduct, category)

}