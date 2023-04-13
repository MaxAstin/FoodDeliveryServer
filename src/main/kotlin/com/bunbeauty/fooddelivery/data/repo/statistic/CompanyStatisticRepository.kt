package com.bunbeauty.fooddelivery.data.repo.statistic

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.company.CompanyEntity
import com.bunbeauty.fooddelivery.data.entity.statistic.CompanyStatisticEntity
import com.bunbeauty.fooddelivery.data.entity.statistic.CompanyStatisticProductEntity
import com.bunbeauty.fooddelivery.data.model.new_statistic.GetStatistic
import com.bunbeauty.fooddelivery.data.model.new_statistic.PeriodType
import com.bunbeauty.fooddelivery.data.model.new_statistic.UpdateStatistic
import com.bunbeauty.fooddelivery.data.model.new_statistic.insert.InsertCompanyStatistic
import com.bunbeauty.fooddelivery.data.model.new_statistic.insert.InsertStatisticProduct
import com.bunbeauty.fooddelivery.data.table.CompanyStatisticProductTable
import com.bunbeauty.fooddelivery.data.table.CompanyStatisticTable
import org.jetbrains.exposed.sql.*
import java.util.*

class CompanyStatisticRepository : ICompanyStatisticRepository {

    override suspend fun getStatisticListByTimePeriodTypeCompany(
        time: Long,
        periodType: PeriodType,
        companyUuid: UUID,
    ): List<GetStatistic> = query {
        CompanyStatisticEntity.find {
            (CompanyStatisticTable.periodType eq periodType.name) and
                    (CompanyStatisticTable.time greaterEq time) and
                    (CompanyStatisticTable.company eq companyUuid)
        }.orderBy(CompanyStatisticTable.time to SortOrder.DESC).map { companyStatisticEntity ->
            companyStatisticEntity.toStatistic()
        }
    }

    override suspend fun getStatisticByTimePeriodTypeCompany(
        time: Long,
        periodType: PeriodType,
        companyUuid: UUID,
    ): GetStatistic? = query {
        CompanyStatisticEntity.find {
            (CompanyStatisticTable.periodType eq periodType.name) and
                    (CompanyStatisticTable.time eq time) and
                    (CompanyStatisticTable.company eq companyUuid)
        }.firstOrNull()
            ?.toStatistic()
    }

    override suspend fun insetStatistic(insertCompanyStatistic: InsertCompanyStatistic) = query {
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

    override suspend fun updateStatistic(statisticUuid: UUID, updateStatistic: UpdateStatistic): GetStatistic? = query {
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

    private fun insertStatisticProducts(
        insertStatisticProductList: List<InsertStatisticProduct>,
        companyStatisticEntity: CompanyStatisticEntity,
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