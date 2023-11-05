package com.bunbeauty.fooddelivery.domain.model.statistic

import kotlinx.serialization.Serializable

@Serializable
class GetStatisticDetails(
    val period: String,
    val orderCount: Int,
    val proceeds: Int,
    val averageCheck: Int,
    val productStatisticList: List<GetProductStatistic>
)