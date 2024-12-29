package com.bunbeauty.fooddelivery.domain.feature.statistic.mapper

import com.bunbeauty.fooddelivery.domain.feature.statistic.model.GetLastMonthCompanyStatistic
import com.bunbeauty.fooddelivery.domain.feature.statistic.model.LastMonthCompanyStatistic

val toGetLastMonthCompanyStatistic: LastMonthCompanyStatistic.() -> GetLastMonthCompanyStatistic = {
    GetLastMonthCompanyStatistic(
        period = period,
        companyName = companyName,
        orderProceeds = orderProceeds
    )
}
