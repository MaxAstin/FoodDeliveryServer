package com.bunbeauty.fooddelivery.data.model.statistic

import kotlinx.serialization.Serializable

@Serializable
class GetStatistic(
    val period: String,
    val startPeriodTime: Long,
    val orderCount: Int,
    val proceeds: Int,
    val averageCheck: Int,
    val productStatisticList: List<GetProductStatistic>
)