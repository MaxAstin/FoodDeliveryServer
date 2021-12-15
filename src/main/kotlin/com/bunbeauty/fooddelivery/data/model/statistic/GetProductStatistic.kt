package com.bunbeauty.fooddelivery.data.model.statistic

import kotlinx.serialization.Serializable

@Serializable
data class GetProductStatistic(
    val name: String,
    val orderCount: Int,
    val productCount: Int,
    val proceeds: Int
)