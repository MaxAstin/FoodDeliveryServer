package com.bunbeauty.fooddelivery.data.model.order

import com.bunbeauty.fooddelivery.data.model.menu_product.GetMenuProduct
import kotlinx.serialization.Serializable

@Serializable
class GetOrderProduct(
    val uuid: String,
    val count: Int,
    val name: String,
    val newPrice: Int,
    val oldPrice: Int?,
    val utils: String?,
    val nutrition: Int?,
    val description: String,
    val comboDescription: String?,
    val photoLink: String,
    val barcode: Int,
    val menuProduct: GetMenuProduct,
    val orderUuid: String,
)