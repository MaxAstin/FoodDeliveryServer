package com.bunbeauty.fooddelivery.domain.model.recommendation

import com.bunbeauty.fooddelivery.domain.model.menu_product.GetMenuProduct
import kotlinx.serialization.Serializable

@Serializable
class GetRecommendation(
    val uuid: String,
    val isVisible: Boolean,
    val menuProduct: GetMenuProduct,
)