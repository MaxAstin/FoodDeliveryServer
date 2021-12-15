package com.bunbeauty.fooddelivery.data.model.menu_product

import com.bunbeauty.fooddelivery.data.model.category.GetCategory
import kotlinx.serialization.Serializable

@Serializable
data class GetMenuProduct(
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
    val categories: List<GetCategory>,
    val isVisible: Boolean,
)
