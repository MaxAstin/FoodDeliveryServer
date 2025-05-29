package com.bunbeauty.fooddelivery.domain.feature.menu.model.category

import kotlinx.serialization.Serializable

@Serializable
class PatchCategoryList(
    val patchCategoryItemList: List<PatchCategoryItem>
) {
    @Serializable
    class PatchCategoryItem(
        val uuid: String,
        val name: String? = null,
        val priority: Int? = null
    )
}
