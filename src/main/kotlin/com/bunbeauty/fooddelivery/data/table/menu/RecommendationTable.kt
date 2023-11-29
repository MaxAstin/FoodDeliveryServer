package com.bunbeauty.fooddelivery.data.table.menu

import org.jetbrains.exposed.dao.id.UUIDTable

@Deprecated("Remove after migration to MenuProductTable - isRecommended")
object RecommendationTable : UUIDTable() {

    val isVisible = bool("isVisible").default(true)

    val menuProduct = reference("menuProduct", MenuProductTable)

}
