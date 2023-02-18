package com.bunbeauty.fooddelivery.service.new_statistic

import com.bunbeauty.fooddelivery.data.ext.toUuid
import com.bunbeauty.fooddelivery.data.model.company.GetCompany
import com.bunbeauty.fooddelivery.data.model.new_statistic.PeriodType
import com.bunbeauty.fooddelivery.data.model.new_statistic.UpdateStatistic
import com.bunbeauty.fooddelivery.data.model.new_statistic.insert.InsertStatistic
import com.bunbeauty.fooddelivery.data.model.new_statistic.insert.InsertStatisticProduct
import com.bunbeauty.fooddelivery.data.model.statistic.GetStatisticOrder
import com.bunbeauty.fooddelivery.data.repo.company.ICompanyRepository
import com.bunbeauty.fooddelivery.data.repo.order.IOrderStatisticRepository
import com.bunbeauty.fooddelivery.data.repo.statistic.IStatisticRepository
import org.joda.time.DateTime
import org.joda.time.DateTimeZone

class NewStatisticService(
    private val orderStatisticRepository: IOrderStatisticRepository,
    private val companyRepository: ICompanyRepository,
    private val statisticRepository: IStatisticRepository,
) {

    suspend fun updateStatistic() {
        companyRepository.getCompanyList().forEach { company ->
            updateLastDayStatistic(company)
        }

        //TODO
        //orderStatisticRepository.getOrderListByCompanyUuid()
    }

    private suspend fun updateLastDayStatistic(company: GetCompany) {
        val toTime = DateTime.now()
            .withZone(DateTimeZone.forOffsetHours(company.offset))
            .withTimeAtStartOfDay()
        val fromTime = toTime.minusDays(1)
        val statisticOrderList = orderStatisticRepository.getOrderListByCompanyUuid(
            companyUuid = company.uuid.toUuid(),
            fromTime = fromTime.millis,
            toTime = toTime.millis,
        )

        val getStatistic = statisticRepository.getStatisticByTimePeriodTypeCompany(
            time = fromTime.millis,
            periodType = PeriodType.DAY,
            companyUuid = company.uuid.toUuid(),
        )
        if (getStatistic == null) {
            val insertStatisticDay = InsertStatistic(
                time = fromTime.millis,
                periodType = PeriodType.DAY,
                orderCount = statisticOrderList.size,
                orderProceeds = calculateOrderProceeds(statisticOrderList),
                statisticProductList = calculateStatisticProductList(statisticOrderList),
                companyUuid = company.uuid.toUuid(),
            )
            statisticRepository.insetStatistic(insertStatisticDay)
        } else {
            val updateStatistic = UpdateStatistic(
                orderCount = statisticOrderList.size,
                orderProceeds = calculateOrderProceeds(statisticOrderList),
                statisticProductList = calculateStatisticProductList(statisticOrderList)
            )
            statisticRepository.updateStatistic(getStatistic.uuid.toUuid(), updateStatistic)
        }
    }

    private fun calculateOrderProceeds(statisticOrderList: List<GetStatisticOrder>): Int {
        return statisticOrderList.sumOf { statisticOrder ->
            statisticOrder.statisticOrderProductList.sumOf { statisticOrderProduct ->
                statisticOrderProduct.newPrice * statisticOrderProduct.count
            }
        }
    }

    private fun calculateStatisticProductList(statisticOrderList: List<GetStatisticOrder>): List<InsertStatisticProduct> {
        return statisticOrderList.flatMap { statisticOrder ->
            statisticOrder.statisticOrderProductList
        }.groupBy { statisticOrderProduct ->
            statisticOrderProduct.menuProductUuid
        }.map { (_, statisticOrderProductList) ->
            InsertStatisticProduct(
                name = statisticOrderProductList.first().name,
                photoLink = statisticOrderProductList.first().photoLink,
                productCount = statisticOrderProductList.sumOf { statisticOrderProduct ->
                    statisticOrderProduct.count
                },
                proceeds = statisticOrderProductList.sumOf { statisticOrderProduct ->
                    statisticOrderProduct.newPrice * statisticOrderProduct.count
                }
            )
        }
    }

}