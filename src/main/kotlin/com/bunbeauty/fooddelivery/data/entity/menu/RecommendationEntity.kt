package com.bunbeauty.fooddelivery.data.entity.menu

import com.bunbeauty.fooddelivery.data.table.menu.RecommendationTable
import com.bunbeauty.fooddelivery.domain.model.recommendation.GetRecommendation
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

@Deprecated("Remove after migration to MenuProductTable - isRecommended")
class RecommendationEntity(uuid: EntityID<UUID>) : UUIDEntity(uuid) {

    val uuid: String = uuid.value.toString()
    var isVisible: Boolean by RecommendationTable.isVisible

    var menuProduct: MenuProductEntity by MenuProductEntity referencedOn RecommendationTable.menuProduct

    companion object : UUIDEntityClass<RecommendationEntity>(RecommendationTable)

    fun toRecommendation() = GetRecommendation(
        uuid = uuid,
        isVisible = isVisible,
        menuProduct = menuProduct.toMenuProduct(),
    )

}