package com.bunbeauty.fooddelivery.domain.feature.menu.model

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
    val categories: List<Category>,
    val isVisible: Boolean,
)
