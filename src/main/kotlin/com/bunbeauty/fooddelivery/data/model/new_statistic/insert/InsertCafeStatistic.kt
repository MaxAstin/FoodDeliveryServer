package com.bunbeauty.fooddelivery.data.model.new_statistic.insert

import com.bunbeauty.fooddelivery.data.model.new_statistic.PeriodType
import java.util.UUID

class InsertCafeStatistic(
    val time: Long,
    val periodType: PeriodType,
    val orderCount: Int,
    val orderProceeds: Int,
    val statisticProductList: List<InsertStatisticProduct>,
    val cafeUuid: UUID
)