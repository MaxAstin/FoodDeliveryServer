package com.bunbeauty.fooddelivery.domain.feature.statistic.model

data class LastMonthCompanyStatistic(
    val period: String,
    val companyName: String,
    val orderProceeds: Int?
)
