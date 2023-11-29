package com.bunbeauty.fooddelivery.domain.feature.menu.model.menuproduct

import kotlinx.serialization.Serializable

@Serializable
class PatchMenuProduct(
    val name: String? = null,
    val newPrice: Int? = null,
    val oldPrice: Int? = null,
    val utils: String? = null,
    val nutrition: Int? = null,
    val description: String? = null,
    val comboDescription: String? = null,
    val photoLink: String? = null,
    val barcode: Int? = null,
    val categoryUuids: List<String>? = null,
    val isVisible: Boolean? = null,
)