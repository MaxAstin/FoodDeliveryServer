package com.bunbeauty.fooddelivery.domain.feature.statistic.model

data class CompanyStatistic(
    val uuid: String,
    val time: Long,
    val periodType: String,
    val orderCount: Int,
    val orderProceeds: Int
)