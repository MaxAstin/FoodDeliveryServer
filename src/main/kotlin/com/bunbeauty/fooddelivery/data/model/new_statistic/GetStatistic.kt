package com.bunbeauty.fooddelivery.data.model.new_statistic

class GetStatistic(
    val uuid: String,
    val time: Long,
    val periodType: PeriodType,
    val orderCount: Int,
    val orderProceeds: Int,
)