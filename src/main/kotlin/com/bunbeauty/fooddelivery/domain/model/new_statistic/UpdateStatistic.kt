package com.bunbeauty.fooddelivery.domain.model.new_statistic

import com.bunbeauty.fooddelivery.domain.model.new_statistic.insert.InsertStatisticProduct

class UpdateStatistic(
    val orderCount: Int,
    val orderProceeds: Int,
    val statisticProductList: List<InsertStatisticProduct>
)
