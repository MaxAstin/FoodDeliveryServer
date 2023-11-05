package com.bunbeauty.fooddelivery.domain.model.new_statistic

import kotlinx.serialization.Serializable

@Serializable
class GetStatistic(
    val uuid: String,
    val time: Long,
    val periodType: PeriodType,
    val orderCount: Int,
    val orderProceeds: Int,
)