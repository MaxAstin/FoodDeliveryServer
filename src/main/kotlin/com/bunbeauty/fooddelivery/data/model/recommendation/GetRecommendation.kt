package com.bunbeauty.fooddelivery.data.model.recommendation

import com.bunbeauty.fooddelivery.data.model.menu_product.GetMenuProduct
import kotlinx.serialization.Serializable

@Serializable
class GetRecommendation(
    val uuid: String,
    val menuProduct: GetMenuProduct,
)