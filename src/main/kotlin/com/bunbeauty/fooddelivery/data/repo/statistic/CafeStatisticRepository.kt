package com.bunbeauty.fooddelivery.data.repo.statistic

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.cafe.CafeEntity
import com.bunbeauty.fooddelivery.data.entity.statistic.CafeStatisticEntity
import com.bunbeauty.fooddelivery.data.entity.statistic.CafeStatisticProductEntity
import com.bunbeauty.fooddelivery.data.table.CafeStatisticProductTable
import com.bunbeauty.fooddelivery.data.table.CafeStatisticTable
import com.bunbeauty.fooddelivery.domain.model.new_statistic.GetStatistic
import com.bunbeauty.fooddelivery.domain.model.new_statistic.PeriodType
import com.bunbeauty.fooddelivery.domain.model.new_statistic.UpdateStatistic
import com.bunbeauty.fooddelivery.domain.model.new_statistic.insert.InsertCafeStatistic
import com.bunbeauty.fooddelivery.domain.model.new_statistic.insert.InsertStatisticProduct
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class CafeStatisticRepository : ICafeStatisticRepository {

    override suspend fun getStatisticListByTimePeriodTypeCafe(
        time: Long,
        periodType: PeriodType,
        cafeUuid: UUID,
        limit: Int
    ): List<GetStatistic> = query {
        CafeStatisticEntity.find {
            (CafeStatisticTable.time greaterEq time) and
                (CafeStatisticTable.periodType eq periodType.name) and
                (CafeStatisticTable.cafe eq cafeUuid)
        }.limit(limit)
            .orderBy(CafeStatisticTable.time to SortOrder.DESC)
            .map { cafeStatisticEntity ->
                cafeStatisticEntity.toStatistic()
            }
    }

    override suspend fun getStatisticByTimePeriodTypeCafe(
        time: Long,
        periodType: PeriodType,
        cafeUuid: UUID
    ): GetStatistic? = query {
        CafeStatisticEntity.find {
            (CafeStatisticTable.periodType eq periodType.name) and
                (CafeStatisticTable.time eq time) and
                (CafeStatisticTable.cafe eq cafeUuid)
        }.firstOrNull()
            ?.toStatistic()
    }

    override suspend fun insetStatistic(insertCafeStatistic: InsertCafeStatistic) = query {
        val cafeStatisticEntity = CafeStatisticEntity.new {
            time = insertCafeStatistic.time
            periodType = insertCafeStatistic.periodType.name
            orderCount = insertCafeStatistic.orderCount
            orderProceeds = insertCafeStatistic.orderProceeds
            cafe = CafeEntity[insertCafeStatistic.cafeUuid]
        }
        insertStatisticProducts(insertCafeStatistic.statisticProductList, cafeStatisticEntity)
        cafeStatisticEntity.toStatistic()
    }

    override suspend fun updateStatistic(statisticUuid: UUID, updateStatistic: UpdateStatistic): GetStatistic? =
        transaction {
            CafeStatisticProductTable.deleteWhere { sqlBuilder ->
                sqlBuilder.run {
                    cafeStatistic eq statisticUuid
                }
            }

            CafeStatisticEntity.findById(statisticUuid)?.also { statisticEntity ->
                statisticEntity.orderCount = updateStatistic.orderCount
                statisticEntity.orderProceeds = updateStatistic.orderProceeds
                insertStatisticProducts(updateStatistic.statisticProductList, statisticEntity)
            }?.toStatistic()
        }

    private fun insertStatisticProducts(
        insertStatisticProductList: List<InsertStatisticProduct>,
        cafeStatisticEntity: CafeStatisticEntity
    ) {
        insertStatisticProductList.onEach { insertStatisticProduct ->
            CafeStatisticProductEntity.new {
                name = insertStatisticProduct.name
                photoLink = insertStatisticProduct.photoLink
                productCount = insertStatisticProduct.productCount
                productProceeds = insertStatisticProduct.proceeds
                cafeStatistic = cafeStatisticEntity
            }
        }
    }
}
