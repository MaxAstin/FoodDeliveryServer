package com.bunbeauty.fooddelivery.data.model.order

import com.bunbeauty.fooddelivery.data.model.menu_product.GetMenuProduct
import kotlinx.serialization.Serializable

@Serializable
data class GetStatisticOrderProduct(
    val uuid: String,
    val count: Int,
    val newPrice: Int,
    val menuProduct: GetMenuProduct,
)