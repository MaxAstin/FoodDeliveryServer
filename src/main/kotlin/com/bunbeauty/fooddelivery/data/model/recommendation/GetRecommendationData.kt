package com.bunbeauty.fooddelivery.data.model.recommendation

import kotlinx.serialization.Serializable

@Serializable
class GetRecommendationData(
    val maxVisibleCount: Int,
    val recommendationList: List<GetRecommendation>,
)