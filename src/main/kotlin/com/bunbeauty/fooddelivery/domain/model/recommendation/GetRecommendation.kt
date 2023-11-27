package com.bunbeauty.fooddelivery.domain.model.recommendation

import com.bunbeauty.fooddelivery.domain.feature.menu.model.GetMenuProduct
import kotlinx.serialization.Serializable

@Serializable
data class GetRecommendation(
    val uuid: String,
    val isVisible: Boolean,
    val menuProduct: GetMenuProduct,
)