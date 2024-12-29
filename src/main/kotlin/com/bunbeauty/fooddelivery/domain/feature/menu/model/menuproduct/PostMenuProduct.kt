package com.bunbeauty.fooddelivery.domain.feature.menu.model.menuproduct

import kotlinx.serialization.Serializable

@Serializable
class PostMenuProduct(
    val name: String,
    val newPrice: Int,
    val oldPrice: Int?,
    val utils: String?,
    val nutrition: Int?,
    val description: String,
    val comboDescription: String?,
    val photoLink: String,
    val barcode: Int,
    val categoryUuids: List<String>,
    val isRecommended: Boolean,
    val isVisible: Boolean
)
