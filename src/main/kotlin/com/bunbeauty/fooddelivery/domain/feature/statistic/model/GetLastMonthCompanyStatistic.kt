package com.bunbeauty.fooddelivery.domain.feature.statistic.model

import kotlinx.serialization.Serializable

@Serializable
class GetLastMonthCompanyStatistic(
    val period: String,
    val companyName: String,
    val orderProceeds: Int?,
)