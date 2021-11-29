package com.bunbeauty.food_delivery.data.table

import org.jetbrains.exposed.sql.Table

object MenuProductCategoryTable: Table() {

    val menuProduct = reference("menuProduct", MenuProductTable)
    val category = reference("category", CategoryTable)

    override val primaryKey = PrimaryKey(menuProduct, category)

}