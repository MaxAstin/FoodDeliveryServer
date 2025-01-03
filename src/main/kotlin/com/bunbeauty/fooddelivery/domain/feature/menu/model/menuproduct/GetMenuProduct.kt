package com.bunbeauty.fooddelivery.domain.feature.menu.model.menuproduct

import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.GetMenuProductAdditionGroup
import com.bunbeauty.fooddelivery.domain.feature.menu.model.category.GetCategory
import kotlinx.serialization.Serializable

@Serializable
class GetMenuProduct(
    val uuid: String,
    val name: String,
    val newPrice: Int,
    val oldPrice: Int?,
    val utils: String?,
    val nutrition: Int?,
    val description: String,
    val comboDescription: String?,
    val photoLink: String,
    val barcode: Int,
    val isRecommended: Boolean,
    val isVisible: Boolean,
    val categories: List<GetCategory>,
    val additionGroups: List<GetMenuProductAdditionGroup>
)
