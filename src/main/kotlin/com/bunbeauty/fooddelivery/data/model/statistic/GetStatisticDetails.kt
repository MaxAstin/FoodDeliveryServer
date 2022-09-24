package com.bunbeauty.fooddelivery.data.model.statistic

import kotlinx.serialization.Serializable

@Serializable
data class GetStatisticDetails(
    val period: String,
    val orderCount: Int,
    val proceeds: Int,
    val averageCheck: Int,
    val productStatisticList: List<GetProductStatistic>
)