package com.bunbeauty.fooddelivery.data.repo.statistic

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.CompanyEntity
import com.bunbeauty.fooddelivery.data.entity.StatisticDayProductEntity
import com.bunbeauty.fooddelivery.data.entity.StatisticEntity
import com.bunbeauty.fooddelivery.data.model.new_statistic.GetStatistic
import com.bunbeauty.fooddelivery.data.model.new_statistic.PeriodType
import com.bunbeauty.fooddelivery.data.model.new_statistic.UpdateStatistic
import com.bunbeauty.fooddelivery.data.model.new_statistic.insert.InsertStatistic
import com.bunbeauty.fooddelivery.data.model.new_statistic.insert.InsertStatisticProduct
import com.bunbeauty.fooddelivery.data.table.StatisticProductTable
import com.bunbeauty.fooddelivery.data.table.StatisticTable
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import java.util.*

class StatisticRepository : IStatisticRepository {

    override suspend fun getStatisticByTimePeriodTypeCompany(
        periodType: PeriodType,
        time: Long,
        companyUuid: UUID,
    ): GetStatistic? = query {
        StatisticEntity.find {
            (StatisticTable.periodType eq periodType.name) and
                    (StatisticTable.time eq time) and
                    (StatisticTable.company eq companyUuid)
        }.firstOrNull()
            ?.toStatistic()
    }

    override suspend fun insetStatistic(insertStatistic: InsertStatistic) = query {
        val statisticEntity = StatisticEntity.new {
            time = insertStatistic.time
            periodType = insertStatistic.periodType.name
            orderCount = insertStatistic.orderCount
            orderProceeds = insertStatistic.orderProceeds
            company = CompanyEntity[insertStatistic.companyUuid]
        }
        insertStatisticProducts(insertStatistic.statisticProductList, statisticEntity)
        statisticEntity.toStatistic()
    }

    override suspend fun updateStatistic(statisticUuid: UUID, updateStatistic: UpdateStatistic): GetStatistic? = query {
        StatisticProductTable.deleteWhere {
            StatisticProductTable.statistic eq statisticUuid
        }

        StatisticEntity.findById(statisticUuid)?.also { statisticEntity ->
            statisticEntity.orderCount = updateStatistic.orderCount
            statisticEntity.orderProceeds = updateStatistic.orderProceeds
            insertStatisticProducts(updateStatistic.statisticProductList, statisticEntity)
        }?.toStatistic()
    }

    private fun insertStatisticProducts(
        insertStatisticProductList: List<InsertStatisticProduct>,
        statisticEntity: StatisticEntity,
    ) {
        insertStatisticProductList.onEach { insertStatisticProduct ->
            StatisticDayProductEntity.new {
                name = insertStatisticProduct.name
                photoLink = insertStatisticProduct.photoLink
                productCount = insertStatisticProduct.productCount
                productProceeds = insertStatisticProduct.proceeds
                statistic = statisticEntity
            }
        }
    }

}