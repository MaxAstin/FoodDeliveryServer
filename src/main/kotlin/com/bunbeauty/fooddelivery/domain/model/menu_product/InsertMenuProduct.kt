package com.bunbeauty.fooddelivery.domain.model.menu_product

import java.util.*

class InsertMenuProduct(
    val name: String,
    val newPrice: Int,
    val oldPrice: Int?,
    val utils: String?,
    val nutrition: Int?,
    val description: String,
    val comboDescription: String?,
    val photoLink: String,
    val barcode: Int,
    val companyUuid: UUID,
    val categoryUuids: List<UUID>,
    val isVisible: Boolean,
)