package com.bunbeauty.fooddelivery.domain.feature.menu.model.menuproduct

import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.AdditionGroup
import com.bunbeauty.fooddelivery.domain.feature.menu.model.category.Category

data class MenuProduct(
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
    val companyUuid: String,
    val categories: List<Category>,
    val additionGroups: List<AdditionGroup>,
)