package com.bunbeauty.fooddelivery.data.repo.statistic

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.CompanyEntity
import com.bunbeauty.fooddelivery.data.entity.StatisticDayEntity
import com.bunbeauty.fooddelivery.data.model.statistic.insert.InsertStatisticDay

class StatisticRepository : IStatisticRepository {

    override suspend fun insetStatisticDay(insertStatisticDay: InsertStatisticDay) = query {
        StatisticDayEntity.new {
            time = insertStatisticDay.time
            orderCount = insertStatisticDay.orderCount
            orderProceeds = insertStatisticDay.orderCount
            company = CompanyEntity[insertStatisticDay.companyUuid]
        }.toStatisticDay()
    }
}