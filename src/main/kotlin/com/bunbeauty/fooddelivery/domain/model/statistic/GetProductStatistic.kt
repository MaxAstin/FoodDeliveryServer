package com.bunbeauty.fooddelivery.domain.model.statistic

import kotlinx.serialization.Serializable

@Serializable
class GetProductStatistic(
    val name: String,
    val orderCount: Int,
    val productCount: Int,
    val proceeds: Int
)