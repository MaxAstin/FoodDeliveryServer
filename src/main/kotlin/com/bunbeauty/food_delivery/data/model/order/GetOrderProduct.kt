package com.bunbeauty.food_delivery.data.model.order

import com.bunbeauty.food_delivery.data.model.menu_product.GetMenuProduct
import kotlinx.serialization.Serializable

@Serializable
data class GetOrderProduct(
    val menuProduct: GetMenuProduct,
    val count: Int,
)
