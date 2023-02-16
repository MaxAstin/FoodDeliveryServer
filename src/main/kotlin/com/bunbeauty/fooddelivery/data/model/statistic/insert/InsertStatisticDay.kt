package com.bunbeauty.fooddelivery.data.model.statistic.insert

import java.util.*

class InsertStatisticDay(
    val time: Long,
    val orderCount: Int,
    val proceeds: Int,
    val statisticProductList: List<InsertStatisticProduct>,
    val companyUuid: UUID

)