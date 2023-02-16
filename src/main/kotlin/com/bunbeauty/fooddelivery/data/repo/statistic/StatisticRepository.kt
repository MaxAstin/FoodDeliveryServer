package com.bunbeauty.fooddelivery.data.repo.statistic

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.CompanyEntity
import com.bunbeauty.fooddelivery.data.entity.StatisticDayEntity
import com.bunbeauty.fooddelivery.data.entity.StatisticDayProductEntity
import com.bunbeauty.fooddelivery.data.ext.toUuid
import com.bunbeauty.fooddelivery.data.model.statistic.insert.InsertStatisticDay

class StatisticRepository : IStatisticRepository {

    override suspend fun insetStatisticDay(insertStatisticDay: InsertStatisticDay) = query {
        val insertedStatisticDay = StatisticDayEntity.new {
            time = insertStatisticDay.time
            orderCount = insertStatisticDay.orderCount
            orderProceeds = insertStatisticDay.proceeds
            company = CompanyEntity[insertStatisticDay.companyUuid]
        }
        insertStatisticDay.statisticProductList.onEach { insertStatisticProduct ->
            StatisticDayProductEntity.new {
                name = insertStatisticProduct.name
                photoLink = insertStatisticProduct.photoLink
                productCount = insertStatisticProduct.productCount
                productProceeds = insertStatisticProduct.proceeds
                statisticDay = insertedStatisticDay
            }
        }
        insertedStatisticDay.toStatisticDay()
    }
}