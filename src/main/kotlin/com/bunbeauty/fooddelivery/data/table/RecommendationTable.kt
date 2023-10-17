package com.bunbeauty.fooddelivery.data.table

import org.jetbrains.exposed.dao.id.UUIDTable

object RecommendationTable : UUIDTable() {

    val menuProduct = reference("menuProduct", MenuProductTable)

}
