package com.bunbeauty.fooddelivery.domain.model.new_statistic.insert

import com.bunbeauty.fooddelivery.domain.model.new_statistic.PeriodType
import java.util.*

class InsertCompanyStatistic(
    val time: Long,
    val periodType: PeriodType,
    val orderCount: Int,
    val orderProceeds: Int,
    val statisticProductList: List<InsertStatisticProduct>,
    val companyUuid: UUID

)