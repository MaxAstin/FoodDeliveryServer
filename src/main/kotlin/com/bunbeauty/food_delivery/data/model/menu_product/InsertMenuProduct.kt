package com.bunbeauty.food_delivery.data.model.menu_product

import java.util.*

data class InsertMenuProduct(
    val name: String,
    val newPrice: Int,
    val oldPrice: Int?,
    val utils: String?,
    val nutrition: Int?,
    val description: String,
    val comboDescription: String?,
    val photoLink: String,
    val barcode: Int,
    val companyUUID: UUID,
    val categoryUuids: List<UUID>,
    val isVisible: Boolean,
)