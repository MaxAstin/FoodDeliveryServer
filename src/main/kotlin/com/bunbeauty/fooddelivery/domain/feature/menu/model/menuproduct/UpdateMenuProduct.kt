package com.bunbeauty.fooddelivery.domain.feature.menu.model.menuproduct

import java.util.*

class UpdateMenuProduct(
    val name: String?,
    val newPrice: Int?,
    val oldPrice: Int?,
    val utils: String?,
    val nutrition: Int?,
    val description: String?,
    val comboDescription: String?,
    val photoLink: String?,
    val barcode: Int?,
    val categoryUuids: List<UUID>?,
    val isRecommended: Boolean?,
    val isVisible: Boolean?,
)