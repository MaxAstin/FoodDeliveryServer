package com.bunbeauty.fooddelivery.data.features.statistic.mapper

import com.bunbeauty.fooddelivery.data.entity.statistic.CompanyStatisticEntity
import com.bunbeauty.fooddelivery.domain.feature.statistic.model.CompanyStatistic

val toCompanyStatistic: CompanyStatisticEntity.() -> CompanyStatistic = {
    CompanyStatistic(
        uuid = uuid,
        time = time,
        periodType = periodType,
        orderCount = orderCount,
        orderProceeds = orderProceeds
    )
}
