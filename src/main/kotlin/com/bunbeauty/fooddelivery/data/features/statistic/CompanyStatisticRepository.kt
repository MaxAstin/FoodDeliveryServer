package com.bunbeauty.fooddelivery.data.features.statistic

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.company.CompanyEntity
import com.bunbeauty.fooddelivery.data.entity.statistic.CompanyStatisticEntity
import com.bunbeauty.fooddelivery.data.entity.statistic.CompanyStatisticProductEntity
import com.bunbeauty.fooddelivery.data.features.statistic.mapper.toCompanyStatistic
import com.bunbeauty.fooddelivery.data.table.CompanyStatisticProductTable
import com.bunbeauty.fooddelivery.data.table.CompanyStatisticTable
import com.bunbeauty.fooddelivery.domain.feature.statistic.model.CompanyStatistic
import com.bunbeauty.fooddelivery.domain.model.new_statistic.GetStatistic
import com.bunbeauty.fooddelivery.domain.model.new_statistic.PeriodType
import com.bunbeauty.fooddelivery.domain.model.new_statistic.UpdateStatistic
import com.bunbeauty.fooddelivery.domain.model.new_statistic.insert.InsertCompanyStatistic
import com.bunbeauty.fooddelivery.domain.model.new_statistic.insert.InsertStatisticProduct
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import java.util.*

class CompanyStatisticRepository {

    suspend fun getStatisticListByTimePeriodTypeCompany(
        time: Long,
        periodType: PeriodType,
        companyUuid: UUID
    ): List<GetStatistic> = query {
        CompanyStatisticEntity.find {
            (CompanyStatisticTable.periodType eq periodType.name) and
                (CompanyStatisticTable.time greaterEq time) and
                (CompanyStatisticTable.company eq companyUuid)
        }.orderBy(CompanyStatisticTable.time to SortOrder.DESC).map { companyStatisticEntity ->
            companyStatisticEntity.toStatistic()
        }
    }

    suspend fun insetStatistic(insertCompanyStatistic: InsertCompanyStatistic) = query {
        val companyStatisticEntity = CompanyStatisticEntity.new {
            time = insertCompanyStatistic.time
            periodType = insertCompanyStatistic.periodType.name
            orderCount = insertCompanyStatistic.orderCount
            orderProceeds = insertCompanyStatistic.orderProceeds
            company = CompanyEntity[insertCompanyStatistic.companyUuid]
        }
        insertStatisticProducts(insertCompanyStatistic.statisticProductList, companyStatisticEntity)
        companyStatisticEntity.toStatistic()
    }

    suspend fun updateStatistic(statisticUuid: UUID, updateStatistic: UpdateStatistic): GetStatistic? = query {
        CompanyStatisticProductTable.deleteWhere { sqlBuilder ->
            sqlBuilder.run {
                companyStatistic eq statisticUuid
            }
        }

        CompanyStatisticEntity.findById(statisticUuid)?.also { statisticEntity ->
            statisticEntity.orderCount = updateStatistic.orderCount
            statisticEntity.orderProceeds = updateStatistic.orderProceeds
            insertStatisticProducts(updateStatistic.statisticProductList, statisticEntity)
        }?.toStatistic()
    }

    suspend fun getStatisticByTimePeriodTypeCompany(
        time: Long,
        periodType: PeriodType,
        companyUuid: UUID
    ): CompanyStatistic? = query {
        CompanyStatisticEntity.find {
            (CompanyStatisticTable.periodType eq periodType.name) and
                (CompanyStatisticTable.time eq time) and
                (CompanyStatisticTable.company eq companyUuid)
        }.firstOrNull()?.toCompanyStatistic()
    }

    private fun insertStatisticProducts(
        insertStatisticProductList: List<InsertStatisticProduct>,
        companyStatisticEntity: CompanyStatisticEntity
    ) {
        insertStatisticProductList.onEach { insertStatisticProduct ->
            CompanyStatisticProductEntity.new {
                name = insertStatisticProduct.name
                photoLink = insertStatisticProduct.photoLink
                productCount = insertStatisticProduct.productCount
                productProceeds = insertStatisticProduct.proceeds
                companyStatistic = companyStatisticEntity
            }
        }
    }
}
